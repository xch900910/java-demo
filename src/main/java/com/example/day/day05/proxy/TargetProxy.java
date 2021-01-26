package com.example.day.day05.proxy;

public class TargetProxy implements Business{
    private final Target target;

    public TargetProxy(Target target) {
        this.target= target;
    }


    @Override
    public void buyHouse() {
        target.buyHouse();
    }
}
