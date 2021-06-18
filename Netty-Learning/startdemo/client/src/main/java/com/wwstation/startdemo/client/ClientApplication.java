package com.wwstation.startdemo.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author william
 * @description
 * @Date: 2021-05-14 12:24
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.wwstation"})
public class ClientApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ClientApplication.class, args);
    }
}
