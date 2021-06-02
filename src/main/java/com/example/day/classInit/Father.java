package com.example.day.classInit;

public class Father {
    public String fStr1 = "father1";
    protected String fStr2 = "father2";
    private String fStr3 = "father3";

    public Father(String fStr1) {
        this.fStr1 = fStr1;
        System.out.println("1 param contract");
    }

    public Father() {
        System.out.println("Father constructor be called");
    }

    {
        System.out.println("Father common block be called");
    }

    static {
        System.out.println("Father static block be called");
    }


}