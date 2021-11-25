package com.wwstation.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author william
 * @description
 * @Date: 2021-11-23 09:39
 */
@SpringBootApplication(scanBasePackages = {"com.wwstation"})
@EnableDubbo
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
