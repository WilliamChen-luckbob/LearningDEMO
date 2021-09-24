package com.wwstation.gracefulthreadpool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author william
 * @description
 * @Date: 2021-09-17 18:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RejectFreeDataParametersDTO {
    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 参数值
     */
    private Object paramValue;
    /**
     * 参数类型
     */
    private Class<?> paramType;
}
