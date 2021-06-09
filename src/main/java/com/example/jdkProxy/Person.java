package com.example.jdkProxy;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-09 08:59
 **/
public class Person implements People {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public void eat() {
        System.out.println(name + " is eating");
    }

    @Override
    public void say() {
        System.out.println(name + " is saying");
    }
}
