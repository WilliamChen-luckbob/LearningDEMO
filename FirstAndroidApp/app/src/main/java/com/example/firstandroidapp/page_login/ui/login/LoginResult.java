package com.example.SimpleSender.page_login.ui.login;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    private Boolean succeed;
    private Integer status;
    private String data;
    private String timestamp;


    private LoginResult setSucceed(Boolean succeed) {
        this.succeed = succeed;
        return this;
    }

    private LoginResult setStatus(Integer status) {
        this.status = status;
        return this;
    }

    private LoginResult setData(String data) {
        this.data = data;
        return this;
    }

    private LoginResult setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Boolean getSucceed() {
        return succeed;
    }

    public Integer getStatus() {
        return status;
    }


    public String getData() {
        return data;
    }


    public String getTimestamp() {
        return timestamp;
    }


    public static LoginResult succeed(Integer status, String data) {
        return new LoginResult()
            .setSucceed(true)
            .setStatus(status)
            .setData(data)
            .setTimestamp(String.valueOf(System.currentTimeMillis()));
    }

    public static LoginResult failed(Integer status, String data) {
        return new LoginResult()
            .setSucceed(false)
            .setStatus(status)
            .setData(data)
            .setTimestamp(String.valueOf(System.currentTimeMillis()));
    }
}