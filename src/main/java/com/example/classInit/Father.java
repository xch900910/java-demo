package com.example.classInit;

public abstract class Father {
    public String fStr1 = "father1";
    protected String fStr2 = "father2";
    private String fStr3 = "father3";


    public Father(String fStr1) {
        this.fStr1 = fStr1;
        System.out.println("1 param contract " + fStr1 + " " + this);
    }

    private Father() {
        System.out.println("Father constructor be called " + this);
    }


    public Father(String fStr1, String fStr2) {
        System.out.println("Father 2constructor be called " + this);
    }

    public static void testStatic() {
        System.out.println("father testStatic");
    }


}