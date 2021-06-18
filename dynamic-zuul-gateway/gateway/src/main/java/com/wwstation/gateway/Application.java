package com.wwstation.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author william
 * @description
 * @Date: 2021-06-18 13:59
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = "com.wwstation")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
