package com.example.SimpleSender.enums;

/**
 * @author william
 * @description
 * @Date: 2021-10-22 11:54
 */
public enum ServiceEnum {
    LOGIN("登录接口", "http://192.168.23.131:8003/login", false),
    SUBMIT_DATA("提交数据","http://192.168.23.131:8018/test/android/connect",true)
    ;

    ServiceEnum(String serviceName, String url, Boolean needToken) {
        this.serviceName = serviceName;
        this.url = url;
        this.needToken = needToken;
    }

    private String serviceName;
    private String url;
    private Boolean needToken;

    public String getServiceName() {
        return serviceName;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getNeedToken() {
        return needToken;
    }
}
