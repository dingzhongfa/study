package com.ding.design.abstractFactory;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class AFactory extends AbstractFactory {
    @Override
    public Engine createEngine() {
        return new EngineA();
    }

    @Override
    public Aircondition createAircondition() {
        return new AirconditionA();
    }
}
