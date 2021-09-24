package com.wwstation.gracefulthreadpool.executables;

import com.alibaba.fastjson.JSONObject;
import com.wwstation.gracefulthreadpool.dto.RejectFreeDataDTO;
import com.wwstation.gracefulthreadpool.dto.RejectFreeDataParametersDTO;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 实现此接口的方法在送入线程池
 *
 * @author william
 * @description
 * @Date: 2021-09-17 12:11
 */
public interface IRejectFreeExecutable {
    /**
     * 获取当前线程的关键入参信息
     *
     * @return
     */
    default List<RejectFreeDataParametersDTO> getData() {
        return new ArrayList<>();
    }

    /**
     * 获取当前线程的执行类或方法名称，便于异常时定位
     *
     * @return
     */
    String getInvocationName();

    //todo 考虑要不要加这个参数构造器
//    /**
//     * 默认
//     * @return
//     */
//    default<T> List<RejectFreeDataParametersDTO> buildParameterList(T clzObject){
//        if (null==clzObject){
//            return new ArrayList<>();
//        }
//        Field[] declaredFields = clzObject.getClass().getDeclaredFields();
//
//    }
}
