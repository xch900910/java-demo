package com.example.day.day02;

/**
 * @author xch900910
 * @version 1.0
 * @Desc
 * @Date 2020/11/15 8:38
 **/
public class Test {

    public static void main(String[] args) {
        try {
            MyThread thread = new MyThread();
            thread.start();
            Thread.sleep(20);//modify 2000 to 20
            thread.interrupt();//请求中断MyThread线程
        } catch (InterruptedException e) {
            System.out.println("main catch");
            e.printStackTrace();
        }
        System.out.println("end!");
    }
}