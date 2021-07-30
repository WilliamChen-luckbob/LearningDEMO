package com.wwstation.logbackdemo.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author william
 * @description
 * @Date: 2021-07-01 18:42
 */
@Slf4j
@Component
public class Logprint {
    @PostConstruct
    public void print() {
        for (int i = 0; i < 10; i++) {
            log.info("日志输出！");
        }

    }
}
