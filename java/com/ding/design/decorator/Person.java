package com.ding.design.decorator;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class Person implements  Human {
    @Override
    public void wearClothes() {
        System.out.println("穿什么衣服");
    }

    @Override
    public void walkToWhere() {
        System.out.println("去哪儿");
    }
}
