package com.ding.design.factory;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class Factory {

    public BMW createBMW(int type) {
        switch (type) {
            case 320:
                return new BMW320();
            case 520:
                return new BMW520();
            default:
                return null;
        }
    }
}
