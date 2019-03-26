package com.ding.design.decorator;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class Main {

    public static void main(String[] args) {
        Decorator2 decorator2 = new Decorator2(new Decorator(new Person()));
        decorator2.wearClothes();
        decorator2.walkToWhere();
    }
}
