package com.example.classInit;


public class Son extends Father {
    public String SStr1 = "Son1";
    protected String SStr2 = "Son2";
    private String SStr3 = "Son3";

    {
        System.out.println("Son common block be called");
    }

    static {
        System.out.println("Son static block be called");
    }

    public Son() {
        System.out.println("Son constructor be called");
    }

    public Son(String SStr1) {
        this.SStr1 = SStr1;
        System.out.println("son 1 param constract");
    }

    public static void main(String[] args) {
        new Son("dddd");
        System.out.println();
        new Son();
    }

}