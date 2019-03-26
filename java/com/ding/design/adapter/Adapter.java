package com.ding.design.adapter;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class Adapter extends Adaptee implements Target {

    @Override
    public void request() {
        super.specialRequest();
    }
}
