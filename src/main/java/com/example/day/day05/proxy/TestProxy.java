package com.example.day.day05.proxy;

public class TestProxy {
    public static void main(String[] args) {
        new TargetProxy(new Target()).buyHouse();
    }
}
