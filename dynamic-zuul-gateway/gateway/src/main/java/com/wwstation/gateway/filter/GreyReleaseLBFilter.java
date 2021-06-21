package com.wwstation.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.wwstation.common.Constants;
import com.wwstation.common.config.RibbonFilterContextHolder;
import com.wwstation.gateway.route.config.GreyReleaseConfig;
import com.wwstation.gateway.route.config.GreyReleaseConfigDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 灰度发布时用于根据配置动态负载均衡的过滤器
 *
 * @author william
 * @description
 * @Date: 2021-05-31 17:55
 */
@Service
@Slf4j
@RefreshScope
public class GreyReleaseLBFilter extends ZuulFilter {
    @Autowired
    private GreyReleaseConfig config;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String method = request.getMethod();

        Map<String, GreyReleaseConfigDetail> urlMap = config.getUrlMap();
        Double v = Math.random() * 10;
        //进入网关后清理tag
        RibbonFilterContextHolder.clearCurrentContext();


        //如果完全没有指定需要灰度的url，使用全局灰度策略
        if (CollectionUtils.isEmpty(urlMap)) {
            if (v.intValue() < config.getGlobalGreyPercentage()) {
                setGreyTag(ctx);
            }
            return null;
        }

        //如果指定了url，不匹配时，全部使用稳定版
        String key = method + "|" + request.getRequestURI();
        if (!urlMap.containsKey(key)) {
            return null;
        }

        //如果指定了url，匹配时，使用指定url上的负载策略
        Integer percentage = urlMap.get(key).getPercentage();

        if (v.intValue() < percentage) {
            //当前线程的threadLocal记录标记，同时生成一个带标记的header传下去
            setGreyTag(ctx);
        }
        return null;
    }

    private void setGreyTag(RequestContext ctx) {
        RibbonFilterContextHolder.getCurrentContext().add(Constants.ATTRIBUTE_GREY_RELEASE_LB_KEY, Constants.ATTRIBUTE_GREY_RELEASE_LB_TEST);
        ctx.addZuulRequestHeader(Constants.ATTRIBUTE_GREY_RELEASE_LB_PASS_HEADER, Constants.ATTRIBUTE_GREY_RELEASE_LB_TEST);
    }
}
