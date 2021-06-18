package com.wwstation.startdemo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author william
 * @description
 * @Date: 2021-05-14 12:25
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wwstation"})
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
