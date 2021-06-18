package com.wwstation.service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author william
 * @description
 * @Date: 2021-06-18 14:02
 */
@EnableFeignClients(basePackages = "com.wwstation")
@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = "com.wwstation")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
