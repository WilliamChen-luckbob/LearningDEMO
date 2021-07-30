package com.wwstation.designmodel.单例模式;


/**
 * @author william
 * @description
 * @Date: 2021-07-21 09:24
 */
public enum Singleton1 {
    /**
     * 实例
     */
    INSTANCE("this is a test");

    private String name;

    Singleton1(String s) {
        name = s;
    }

    public void print() {
        System.out.println("printing " + name);
    }
}
