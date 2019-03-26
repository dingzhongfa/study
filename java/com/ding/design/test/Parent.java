package com.ding.design.test;

/**
 * desription:
 * Created by ding on 2018/2/24.
 */
public class Parent {

    private String name;

    private int age;

    public Parent(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void out() {
        System.out.println("name = " + name + "\t\tage = " + age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
