package com.ding.design.single;

/**
 * desription: 懒汉式变种
 * Created by ding on 2018/2/23.
 */
public class Singleton3 {

    private static Singleton3 singleton3;

    private Singleton3(){

    }

    public static Singleton3 getInstance(){
        synchronized (singleton3){
            if (singleton3==null){
                singleton3 = new Singleton3();
            }
            return singleton3;
        }
    }


}
