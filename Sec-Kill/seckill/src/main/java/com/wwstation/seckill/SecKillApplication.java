package com.wwstation.seckill;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author william
 * @description
 * @Date: 2021-11-23 10:49
 */
@SpringBootApplication(scanBasePackages = {"com.wwstation"})
@EnableDubbo
public class SecKillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecKillApplication.class, args);
    }
}
