package com.wwstation.aspectj_demo.aop_using_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 实际被调用的方法
 *
 * @author william
 * @description
 * @Date: 2021-10-15 16:45
 */
@Slf4j
@Service
public class ProcessService {
    public void process() {
        log.info("now in process(),thread name={}", Thread.currentThread().getName());
    }
}
