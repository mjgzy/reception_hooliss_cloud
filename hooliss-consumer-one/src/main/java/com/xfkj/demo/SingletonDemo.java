package com.xfkj.demo;

import java.util.HashSet;

public class SingletonDemo {
    private static volatile SingletonDemo singletonDemo;
    private SingletonDemo(){
        System.err.println("执行了>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    HashSet hashSet;
    //DCL双端加锁机制
    public static SingletonDemo getInstance(){
        if(singletonDemo==null){
            synchronized (SingletonDemo.class){
                if(singletonDemo==null){
                    singletonDemo=  new SingletonDemo();
                }
            }
        }
        return singletonDemo;
    }

    public static void main(String[] args) {
        SingletonDemo singletonDemo = new SingletonDemo();
        new Thread(()->{
            SingletonDemo.getInstance();
        });
    }
}
