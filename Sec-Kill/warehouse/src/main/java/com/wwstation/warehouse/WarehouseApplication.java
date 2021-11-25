package com.wwstation.warehouse;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author william
 * @description
 * @Date: 2021-11-23 10:37
 */
@SpringBootApplication(scanBasePackages = {"com.wwstation"})
@EnableDubbo
@MapperScan(basePackages = {"com.wwstation.warehouse.mapper"})
public class WarehouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }
}
