package com.example.designPattern.proxy;

public class TestProxy {
    public static void main(String[] args) {
        new TargetProxy(new Target()).buyHouse();
    }
}
