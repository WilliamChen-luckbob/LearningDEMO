package com.wwstation.common;

import lombok.Data;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.regex.RegexUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class CommonResult<T> implements Serializable {
    private static final long serialVersionUID = -9221050412194166989L;

    private T data;
    private String note;//只有当status为500时才有相关提示信息。比如：创建用户的时候，用户已经存在。
    private String status = "200";//200成功，403：权限不够。401：未登录或会话过期。444：账号或者密码错误。500：系统某些错误，如果是500，则note字段会填写相关错误信息
    private int size;//如果回传的是列表，此为列表的size
    private String token;//返回成功 一定要加上token
    private Object dim;
    private Object config;

    public CommonResult() {

    }

    public CommonResult(String token) {
        this.token = token;
    }

    public CommonResult(int size) {
        this.size = size;
    }

    public CommonResult(String status, String token, String note) {
        this.status = status;
        this.token = token;
        this.note = note;
    }

    public CommonResult(String status, String token) {
        this.status = status;
        this.token = token;
    }

    public CommonResult(String status, String token, int size) {
        this.status = status;
        this.token = token;
        this.size = size;
    }

    public static CommonResult succeed(Object data) {
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("200");
        if (null != data) {
            commonResult.setData(data);
            if (Collection.class.isAssignableFrom(data.getClass())) {
                commonResult.setSize(((Collection) data).size());
            } else if (Map.class.isAssignableFrom(data.getClass())) {
                commonResult.setSize(((Map) data).size());
            }
        }
        return commonResult;
    }

    private static CommonResult succeed(String msg) {
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("200");
        if (null != msg) {
            commonResult.setNote(msg);
        }
        return commonResult;
    }

    public static CommonResult succeed() {
        return succeed("成功");
    }

    public static CommonResult fail(String msg) {
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus("500");
        commonResult.setNote(msg);
        return commonResult;
    }

    public static CommonResult fail(Integer code, String msg) {
        CommonResult commonResult = new CommonResult();
        commonResult.setStatus(code.toString());
        if (StringUtils.isNotEmpty(msg)) {
            commonResult.setNote(msg);
        }
        return commonResult;
    }

}

