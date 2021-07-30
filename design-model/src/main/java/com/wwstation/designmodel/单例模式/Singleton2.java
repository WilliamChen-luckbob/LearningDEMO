package com.wwstation.designmodel.单例模式;

/**
 * 饿汉式加载
 *
 * @author william
 * @description
 * @Date: 2021-07-21 09:31
 */
public class Singleton2 {
    private static final Singleton2 INSTANCE = new Singleton2();

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return INSTANCE;
    }

    public void print(){
        System.out.println("this is an invoke of singleton2");
    }
}
