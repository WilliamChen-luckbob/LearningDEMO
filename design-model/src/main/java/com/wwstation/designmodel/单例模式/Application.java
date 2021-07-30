package com.wwstation.designmodel.单例模式;

/**
 * @author william
 * @description
 * @Date: 2021-07-21 09:24
 */
public class Application {
    public static void main(String[] args) {
        Singleton1 instance = Singleton1.INSTANCE;
        instance.print();
        Singleton2 singleton2 = Singleton2.getInstance();
        Singleton2 singleton21 = Singleton2.getInstance();
        singleton2.print();
        System.out.println(singleton2==singleton21);
    }
}
