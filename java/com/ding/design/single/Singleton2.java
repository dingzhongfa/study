package com.ding.design.single;

/**
 * desription: 饿汉式
 * Created by ding on 2018/2/23.
 */
public class Singleton2 {

    private static Singleton2 singleton2 = new Singleton2();

    private Singleton2(){}

    public static Singleton2 getInstance(){
        return singleton2;
    }
}
