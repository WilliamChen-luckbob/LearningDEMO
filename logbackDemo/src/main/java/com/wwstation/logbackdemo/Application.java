package com.wwstation.logbackdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author william
 * @description
 * @Date: 2021-07-01 18:35
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.wwstation")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
