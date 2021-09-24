package com.wwstation.gracefulthreadpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author william
 * @description
 * @Date: 2021-09-17 11:54
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.wwstation")
@EnableAsync
public class GracefulThreadPoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(GracefulThreadPoolApplication.class, args);
    }
}
