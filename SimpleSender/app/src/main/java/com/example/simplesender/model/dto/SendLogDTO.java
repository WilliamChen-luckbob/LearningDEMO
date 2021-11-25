package com.example.simplesender.model.dto;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author william
 * @description
 * @Date: 2021-10-25 11:50
 */
public class SendLog {
    private Integer id;
    private String code;
    private String sendTime;
    private Integer succeed;

    public SendLog(String code, Integer succeed) {
        this.code = code;
        this.sendTime =new Date().toString();
        this.succeed = succeed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getSucceed() {
        return succeed;
    }

    public void setSucceed(Integer succeed) {
        this.succeed = succeed;
    }
}
