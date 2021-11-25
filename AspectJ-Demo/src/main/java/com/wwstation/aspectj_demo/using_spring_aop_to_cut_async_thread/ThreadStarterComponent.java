package com.wwstation.aspectj_demo.aop_using_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 用于模拟工作的守护线程
 *
 * @author william
 * @description
 * @Date: 2021-10-15 15:20
 */
@Component
@Slf4j
public class ThreadStarterComponent {

    @PostConstruct
    public void initialize() {
//        initByService();
        initDirectly();
    }

    private void initDirectly() {

    }

    private void initByService() {
        Thread daemonThread = new Thread(new RunningDaemonThread(), "测试用守护线程");
        daemonThread.start();
        log.info("测试守护线程已启动！");
    }
}
