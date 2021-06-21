package com.wwstation.service1;

import com.wwstation.common.CommonResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author william
 * @description
 * @Date: 2021-06-18 15:15
 */
@RestController
@RequestMapping("/test/conn")

public class TestController {
    @Value("${server.port:}")
    private String port;
    @Value("${spring.application.name:}")
    private String applicationName;
    @Value("${eureka.instance.metadata-map.greyReleaseTag:}")
    private String releaseTag;
    @Resource
    private API api;

    @GetMapping
    public CommonResult conn() {

        return CommonResult.succeed("收到请求，当前服务为" + applicationName + ":" + port + "\n版本信息为" + getString());
    }

    private String getString() {
        return StringUtils.isEmpty(releaseTag) ? "Stable" : releaseTag;
    }

    @GetMapping("/feign")
    public CommonResult feignConn() {
        return CommonResult.succeed("收到请求，当前服务为" + applicationName + ":" + port + "\n收到的数据为：" + api.conn() + "\n版本信息为" + getString());
    }
}
