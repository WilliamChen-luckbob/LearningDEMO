package exceptions;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import com.wwstation.common.CommonResult;
import com.wwstation.common.Constants;
import com.wwstation.common.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 网关过滤器异常断言类
 *
 * @author william
 * @description
 * @Date: 2020-12-08 10:03
 */
@Slf4j
public class FilterAsserts {
    public static void normalFail(String msg) {
        RequestContext ctx = RequestContext.getCurrentContext();
        log.error("鉴权网关异常：" + msg);
        HttpServletRequest request = ctx.getRequest();
        log.error("请求的url = " + request.getRequestURL());
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);

        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("500");
        commonResult.setNote(msg);
        HttpServletResponse response = ctx.getResponse();
        request.setAttribute(Constants.ATTRIBUTE_PASSED, false);

        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.setCharacterEncoding("utf-8");

        try {
            response.getOutputStream().write(JSONObject.toJSONString(commonResult).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            ctx.setResponseBody(JSONObject.toJSONString(commonResult));
        }
    }
    public static void authFail(String msg) {
        RequestContext ctx = RequestContext.getCurrentContext();
        log.error("鉴权网关异常：" + msg);
        HttpServletRequest request = ctx.getRequest();
        log.error("请求的url = " + request.getRequestURL());
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);

        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("401");
        commonResult.setNote(msg);
        HttpServletResponse response = ctx.getResponse();
        request.setAttribute(Constants.ATTRIBUTE_PASSED, false);

        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.setCharacterEncoding("utf-8");

        try {
            response.getOutputStream().write(JSONObject.toJSONString(commonResult).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            ctx.setResponseBody(JSONObject.toJSONString(commonResult));
        }
    }
    public static void notFound(GatewayNotFoundException e) {
        RequestContext ctx = RequestContext.getCurrentContext();
        log.error("鉴权网关异常：" + e.getMessage());
        HttpServletRequest request = ctx.getRequest();
        log.error("请求的url = " + request.getRequestURL());
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(404);

        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("404");
        commonResult.setNote(e.getMessage());
        HttpServletResponse response = ctx.getResponse();
        request.setAttribute(Constants.ATTRIBUTE_PASSED, false);

        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.setCharacterEncoding("utf-8");

        try {
            response.getOutputStream().write(JSONObject.toJSONString(commonResult).getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
            ctx.setResponseBody(JSONObject.toJSONString(commonResult));
        }
    }

    public static void noBalance(String message) {
        RequestContext ctx = RequestContext.getCurrentContext();

        String moduleName = RegexUtils.getString("Load balancer does not have available server for client:", "", message);
        String errString = "服务"+moduleName+" 正在加载，请稍候！";

        log.error(message);
        HttpServletRequest request = ctx.getRequest();
        log.error("请求的url = " + request.getRequestURL());
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);

        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("500");
        commonResult.setNote(errString);
        HttpServletResponse response = ctx.getResponse();
        request.setAttribute(Constants.ATTRIBUTE_PASSED, false);

        response.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        response.setCharacterEncoding("utf-8");

        try {
            response.getOutputStream().write(JSONObject.toJSONString(commonResult).getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
            ctx.setResponseBody(JSONObject.toJSONString(commonResult));
        }
    }

}
