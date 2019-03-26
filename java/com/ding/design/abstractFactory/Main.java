package com.ding.design.abstractFactory;


/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class Main {

    public static void main(String[] args) {
        AbstractFactory aFactory = new AFactory();
        EngineA engineA = (EngineA) aFactory.createEngine();
        AirconditionA airconditionA = (AirconditionA) aFactory.createAircondition();

        System.out.println();

        AbstractFactory bFactory = new BFactory();
        EngineB engineB = (EngineB) bFactory.createEngine();
        AirconditionB airconditionB = (AirconditionB) bFactory.createAircondition();

    }
}
