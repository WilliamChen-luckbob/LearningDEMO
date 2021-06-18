package com.wwstation.service2;

import com.wwstation.common.CommonResult;
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
    @Resource
    private API api;

    @GetMapping
    public CommonResult conn() {
        return CommonResult.succeed("收到请求，当前服务为" + applicationName + ":" + port);
    }

    @GetMapping("/feign")
    public CommonResult feignConn() {
        return CommonResult.succeed("收到请求，当前服务为" + applicationName + ":" + port + "\n收到的数据为：" + api.conn());
    }
}
