package com.example.SimpleSender.page_login.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private Integer userId;
    private String displayName;
    private String token;

    public LoggedInUser(Integer userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public LoggedInUser(Integer userId, String displayName, String token) {
        this.userId = userId;
        this.displayName = displayName;
        this.token=token;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getToken() {
        return token;
    }
}