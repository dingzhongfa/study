package com.ding.design.prototype;

/**
 * desription:
 * Created by ding on 2018/2/23.
 */
public class Main {

    public static void main(String[] args) {
        Prototype prototype1 = new Prototype();
        System.out.println("prototype1 stringBuilder is "+prototype1.stringBuilder);
        prototype1.stringBuilder.append(4);
        Prototype prototype2 = prototype1.clone();
        System.out.println("prototype2 stringBuilder is "+prototype2.stringBuilder);
        prototype1.stringBuilder.append(5);
        System.out.println("prototype1 stringBuilder is changed");
        System.out.println("prototype1 stringBuilder is "+prototype1.stringBuilder);
        System.out.println("prototype2 stringBuilder is "+prototype2.stringBuilder);
    }
}
