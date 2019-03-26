package com.ding.design.factory;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class Main {

    public static void main(String[] args) {

        // 简单工厂
        Factory factory = new Factory();
        BMW bmw320 = factory.createBMW(320);
        BMW bmw520 = factory.createBMW(520);

        System.out.println();
        // 工厂看方法
        BMWFactory bmw320Factory = new BMW320Factory();
        bmw320 = bmw320Factory.createBMW();
        BMWFactory bmw520Factory = new BMW520Factory();
        bmw520 = bmw520Factory.createBMW();
    }
}
