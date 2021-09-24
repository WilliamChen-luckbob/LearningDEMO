package com.wwstation.gracefulthreadpool.executables.test_executables;

import com.alibaba.fastjson.JSONObject;
import com.wwstation.gracefulthreadpool.dto.RejectFreeDataParametersDTO;
import com.wwstation.gracefulthreadpool.executables.IRejectFreeRunnable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author william
 * @description
 * @Date: 2021-09-17 12:10
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class TestRunnableWithArgs implements IRejectFreeRunnable {

    private String arg1;
    private String arg2;
    private String arg3;
    public String arg4;

    @Override
    public List<RejectFreeDataParametersDTO> getData() {
        ArrayList<RejectFreeDataParametersDTO> result = new ArrayList<>();
        result.add(new RejectFreeDataParametersDTO("arg1", arg1, Integer.class));
        result.add(new RejectFreeDataParametersDTO("arg2", arg2, Integer.class));
        result.add(new RejectFreeDataParametersDTO("arg3", arg3, Integer.class));
        result.add(new RejectFreeDataParametersDTO("arg4", arg4, Integer.class));
        return result;
    }

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
