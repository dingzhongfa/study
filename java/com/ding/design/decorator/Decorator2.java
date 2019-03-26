package com.ding.design.decorator;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class Decorator2 extends Decorator {


    public Decorator2(Human human) {
        super(human);
    }

    private void getClothe(){
        System.out.println("找到衣服");
    }

    private void getTarget(){
        System.out.println("巴黎不错");
    }

    @Override
    public void wearClothes() {
        human.wearClothes();
        getClothe();
    }

    @Override
    public void walkToWhere() {
        human.walkToWhere();
        getTarget();
    }
}
