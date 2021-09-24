package com.wwstation.dynamiclogback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author william
 * @description
 * @Date: 2021-08-20 12:31
 */
@SpringBootApplication
@ComponentScan("com.wwstation")
public class DynamicLogbackApplication {
    public static void main(String[] args) {
        SpringApplication.run(DynamicLogbackApplication.class, args);
    }
}
