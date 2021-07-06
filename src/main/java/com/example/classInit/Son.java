package com.example.classInit;


public class Son extends Father {
    public String SStr1 = "Son1";
    protected String SStr2 = "Son2";
    private String SStr3 = "Son3";



    public Son() {
        super("22");
        System.out.println("Son constructor be called");
    }

    public Son(String SStr1) {
        super("555");
        this.SStr1 = SStr1;
        System.out.println("son 1 param constract");
    }


    public static void main(String[] args) {
        Son son = new Son("dddd");
        System.out.println();
        Son2 son2 = new Son2();
        System.out.println(son.fStr1);
        System.out.println(son2.fStr1);
        Son.testStatic();
//        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
//
//        for (int i = 0; i < 1000000; i++) {
//            objectObjectHashMap.put(i*16, RandomUtil.randInMod(10));
//            System.out.println("size:"+objectObjectHashMap.size()+" i:"+i);
//        }

    }

}