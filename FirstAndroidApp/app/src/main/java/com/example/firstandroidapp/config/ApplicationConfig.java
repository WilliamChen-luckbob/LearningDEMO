package com.example.SimpleSender.config;

import com.example.SimpleSender.page_login.data.model.LoggedInUser;

/**
 * @author william
 * @description
 * @Date: 2021-10-21 16:40
 */
public class ApplicationConfig {


    private static String token;

    private static LoggedInUser user;

    private ApplicationConfig() {
    }

    public static String getToken() {
        return token;
    }

    public static Boolean hastoken() {
        return token != null && !token.isEmpty();
    }

    public static void setToken(String newToken) {
        token = newToken;
    }

    public static LoggedInUser getUser() {
        return user;
    }

    public static void setUser(LoggedInUser user) {
        ApplicationConfig.user = user;
    }
}
