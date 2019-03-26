package com.ding.design.adapter;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class Main {

    public static void main(String[] args) {
        Target concreteTarget = new ConcreteTarget();
        concreteTarget.request();
        Target adapterTarget = new Adapter();
        adapterTarget.request();
    }
}
