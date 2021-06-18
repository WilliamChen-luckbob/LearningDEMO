package com.wwstation.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author william
 * @description
 * @Date: 2021-06-18 13:55
 */
@EnableEurekaServer
@SpringBootApplication
@ComponentScan(basePackages = "com.wwstation")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
