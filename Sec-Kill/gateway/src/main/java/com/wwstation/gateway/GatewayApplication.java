package com.wwstation.gateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author william
 * @description
 * @Date: 2021-11-24 12:13
 */
@SpringBootApplication(scanBasePackages = {"com.wwstation"})
@EnableDiscoveryClient
@EnableDubbo
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
