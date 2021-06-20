package com.wwstation.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.wwstation.common.Constants;
import com.wwstation.common.RegexUtils;
import com.wwstation.gateway.exceptions.FilterAsserts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义的异常处理，为了专门处理路由时由于服务down掉负载均衡无法找到服务而抛出的forwarding error异常
 *
 * @author william
 * @description
 * @Date: 2021-04-06 17:38
 */
@Service
@Slf4j
public class CustomizedErrorFilter extends ZuulFilter {
    protected static final String SEND_ERROR_FILTER_RAN = "sendErrorFilter.ran";
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        Throwable throwable = currentContext.getThrowable();
        return ZuulException.class.isAssignableFrom(throwable.getClass());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        Throwable throwable = currentContext.getThrowable();
        Throwable originCause = getOriginCause(throwable);
        currentContext.set(SEND_ERROR_FILTER_RAN);

        if (originCause.getMessage().contains("Load balancer does not have available server for client")) {
            currentContext.set(SEND_ERROR_FILTER_RAN);
            FilterAsserts.noBalance(originCause.getMessage());
            String moduleName = RegexUtils.getString("Load balancer does not have available server for client:", "", originCause.getMessage());
            setFailedMessage(currentContext, "调用失败，服务" + moduleName + "还没有准备好");
        } else {
            setFailedMessage(currentContext, "未捕获的网关异常！");
            log.error("出现了未知的网关异常！");
            throwable.printStackTrace();
        }
        return null;
    }

    private void setFailedMessage(RequestContext currentContext, String message) {
        HttpServletRequest request = currentContext.getRequest();
        request.setAttribute(Constants.ATTRIBUTE_PASSED, false);
        request.setAttribute(Constants.ATTRIBUTE_FAILED_REASON, message);
    }

    private Throwable getOriginCause(Throwable throwable) {
        if (throwable.getCause() != null) {
            return getOriginCause(throwable.getCause());
        }
        return throwable;
    }
}
