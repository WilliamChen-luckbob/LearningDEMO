package com.wwstation.common.model.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用输出
 *
 * @author william
 * @description
 * @Date: 2021-11-23 10:22
 */
@Data
public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> succeed() {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("成功");
        return result;
    }

    public static <T> Result<T> succeed(String message) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> succeed(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> succeed(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("成功");
        result.setData(data);
        return result;
    }

    public static Result failed() {
        Result result = new Result();
        result.setCode(500);
        result.setMessage("失败");
        return result;
    }


    public static Result failed(String message) {
        Result result = new Result();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> failed(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failed(T data) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage("失败");
        result.setData(data);
        return result;
    }
}
