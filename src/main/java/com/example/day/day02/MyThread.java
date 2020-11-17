package com.example.day.day02;

/**
 * @author xch900910
 * @version 1.0
 * @Desc
 * @Date 2020/11/15 8:38
 **/
public class MyThread extends Thread {
    @Override
    public void run() {
        super.run();
        try{
            for (int i = 0; i < 500000; i++) {
                if (this.interrupted()) {
                    System.out.println("should be stopped and exit");
                    throw new InterruptedException();
                }
                System.out.println("i=" + (i + 1));
            }
            System.out.println("this line cannot be executed. cause thread throws exception");
        }catch(InterruptedException e){
            /**这样处理不好
             * System.out.println("catch interrupted exception");
             * e.printStackTrace();
             */
            Thread.currentThread().interrupt();//这样处理比较好
        }
    }
}