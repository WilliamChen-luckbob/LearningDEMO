package com.wwstation.gracefulthreadpool.dto;

import lombok.Data;

import java.util.List;

/**
 * 当线程池出现冲爆时，将被忽略的数据装入此实体，并输出相应日志
 * @author william
 * @description
 * @Date: 2021-09-17 18:17
 */
@Data
public class RejectFreeDataDTO {
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 方法返回值
     */
    private Class<?> methodReturnType;
    /**
     * 方法参数
     */
    private List<RejectFreeDataParametersDTO> data;
    /**
     * 失败原因
     */
    private String failedReson;
}
