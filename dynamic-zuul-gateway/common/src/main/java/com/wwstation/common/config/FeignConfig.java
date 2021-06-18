package com.wwstation.common.config;

import com.wwstation.common.Constants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 在兼容旧网关时发现一个问题
 * 旧的web-system在发送restTemplate请求时，会带入默认的accept请求头
 * application/json, application/xml, application/*+json, text/xml, application/*+xml
 * 会导致优先解析xml从而令响应失效
 *
 * @author william
 * @description
 * @Date: 2021-01-05 14:59
 */
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Autowired
    RequestContextFilter requestContextFilter;
    @Autowired
    DispatcherServlet dispatcherServlet;

    @PostConstruct
    public void init() {
        // 设置线程继承属性为true，便于子线程获取到父线程的request,两个都设置为了保险。
        requestContextFilter.setThreadContextInheritable(true);
        dispatcherServlet.setThreadContextInheritable(true);
    }

    @Override
    public void apply(RequestTemplate template) {
//        template.header("accept", "*/*");
//        template.header("content-type", "application/json");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                Boolean hasAccept = false;
                Boolean hasGreyTag = false;
                //在循环中删除数据有可能莫名报了空指针
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    if (name.equalsIgnoreCase("accept")) {
                        hasAccept = true;
                    }
                    if (name.equalsIgnoreCase(Constants.ATTRIBUTE_GREY_RELEASE_LB_KEY)) {
                        hasGreyTag = true;
                    }
                }
                template.removeHeader("content-length");
                if (hasAccept) {
                    template.removeHeader("accept");
                    template.header("accept", "*/*");
                }
                //如果header中没有灰度标签，给它加一个
                final String greyTag = SerializationUtils.clone(RibbonFilterContextHolder.getCurrentContext().get(Constants.ATTRIBUTE_GREY_RELEASE_LB_KEY));
                if (!hasGreyTag) {
                    template.header(Constants.ATTRIBUTE_GREY_RELEASE_LB_PASS_HEADER,
                        greyTag);
                }
            }
        }
    }
}
