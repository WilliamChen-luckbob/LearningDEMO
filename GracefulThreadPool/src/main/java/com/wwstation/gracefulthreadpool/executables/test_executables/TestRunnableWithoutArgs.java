package com.wwstation.gracefulthreadpool.executables.test_executables;

import com.wwstation.gracefulthreadpool.dto.RejectFreeDataParametersDTO;
import com.wwstation.gracefulthreadpool.executables.IRejectFreeRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author william
 * @description
 * @Date: 2021-09-17 12:10
 */
@Slf4j
public class TestRunnableWithoutArgs implements IRejectFreeRunnable {
    @Override
    public String getInvocationName() {
        return this.getClass().getName();
    }

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
