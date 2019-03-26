package com.ding.design.decorator;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class Decorator implements Human {

    protected Human human;

    public Decorator(Human human) {
        this.human = human;
    }

    private void goHome() {
        System.out.println("进房子");
    }

    private void lockMap() {
        System.out.println("到map上找");
    }

    @Override
    public void wearClothes() {
        human.wearClothes();
        goHome();
    }

    @Override
    public void walkToWhere() {
        human.walkToWhere();
        lockMap();
    }
}
