package com.wwstation.gateway.exceptions;

import lombok.Getter;

/**
 * @author william
 * @description
 * @Date: 2020-12-07 10:40
 */
@Getter
public class GatewayNotFoundException extends RuntimeException {
    private String status;

    public GatewayNotFoundException() {
        super("没有可以访问的url资源，请确认路径和请求方法是否正确");
        this.status = "404";
    }

    public GatewayNotFoundException(String message) {
        super(message);
        this.status = "404";
    }
}
