package com.ding.design.factory;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class BMW320Factory extends BMWFactory {

    @Override
    public BMW createBMW() {
        return new BMW320();
    }
}
