package com.ding.design.single;

/**
 * desription: 懒汉式
 * Created by ding on 2018/2/23.
 */
public class Singleton1 {

    private static Singleton1 singleton1;

    private Singleton1() {

    }

    public static Singleton1 getInstance() {
        if (singleton1 == null) {
            singleton1 = new Singleton1();
        }
        return singleton1;
    }
}
