package com.ding.design.adapter;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class ConcreteTarget implements Target {

    @Override
    public void request() {
        System.out.println("普通请求");
    }
}
