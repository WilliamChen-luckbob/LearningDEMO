package com.wwstation.common.config;

import com.wwstation.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 为feign调用提供灰度支持
 *
 * @author william
 * @description
 * @Date: 2021-06-07 10:58
 */
@Configuration
@Slf4j
public class FeignGreyConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                //由于tag在服务间调用时无法传递，默认将其放入header中，然后在此取出放入自定义的threadLocal
                String headerKey = Constants.ATTRIBUTE_GREY_RELEASE_LB_PASS_HEADER;
                String key = Constants.ATTRIBUTE_GREY_RELEASE_LB_KEY;
                String greyTag = request.getHeader(headerKey);
                if (StringUtils.isNotEmpty(greyTag)) {
                    RibbonFilterContextHolder.getCurrentContext()
                        .add(key, greyTag);
                }
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                RibbonFilterContextHolder.clearCurrentContext();
            }
        });

    }
}
