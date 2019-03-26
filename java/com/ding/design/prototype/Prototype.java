package com.ding.design.prototype;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class Prototype implements Cloneable {

    public StringBuilder stringBuilder = new StringBuilder("123");

    public Prototype clone() {
        Prototype prototype = null;
        try {
            prototype = (Prototype) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("浅克隆-->失败");
        }
        return prototype;
    }
}
