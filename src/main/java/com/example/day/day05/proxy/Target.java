package com.example.day.day05.proxy;

public class Target implements Business{
    private String name;

    public void buyHouse(){
        System.out.printf("i want buy house");
    }
}
