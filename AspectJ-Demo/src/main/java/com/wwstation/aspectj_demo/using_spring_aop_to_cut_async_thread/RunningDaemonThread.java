package com.wwstation.aspectj_demo.aop_using_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author william
 * @description
 * @Date: 2021-10-15 15:31
 */
@Slf4j
public class RunningDaemonThread implements Runnable {

    @Override
    public void run() {
        while (true) {
            log.info("now in run(),thread name={}", Thread.currentThread().getName());
            ProcessService processService = ApplicationContextHolder.getBean(ProcessService.class);
            processService.process();
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
