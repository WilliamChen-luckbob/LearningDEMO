package com.wwstation.gracefulthreadpool.executables.test_executables;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author william
 * @description
 * @Date: 2021-09-18 15:38
 */
@AllArgsConstructor
@Slf4j
public class TestRunnableNormalWithoutArgs implements Runnable {
    @Override
    public void run() {
        log.info("线程开始执行命令");
        try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("线程执行完毕");
    }
}
