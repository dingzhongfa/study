package com.ding.design.abstractFactory;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class BFactory extends AbstractFactory {
    @Override
    public Engine createEngine() {
        return new EngineB();
    }

    @Override
    public Aircondition createAircondition() {
        return new AirconditionB();
    }
}
