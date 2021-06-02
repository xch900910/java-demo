package com.example.day.threadTest.demo1;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-11-13 18:28
 **/
public class ThreadDemo {
    public static void main(String[] args) {
         Object object = new Object();
        Thread thread1 = new Thread(() -> {
            synchronized (object) { // 获取监视器锁
                try {
                    System.out.println(Thread.currentThread() + "开始等待");
                    object.wait(); // 这里会解锁，这里会解锁，这里会解锁
                    // 顺便提一下，只是解了object上的监视器锁，如果这个线程还持有其他对象的监视器锁，这个时候是不会释放的。
                    System.out.println(Thread.currentThread() + "等待结束");
                } catch (InterruptedException e) {
                    // do somethings
                    System.out.println(Thread.currentThread()+"抛出异常"+e);
                }
            }
        }, "thread1");

        Thread thread2 = new Thread(() -> {
            synchronized (object) { // 获取监视器锁
                try {
                    System.out.println(Thread.currentThread() + "开始等待");
                    object.wait(); // 这里会解锁，这里会解锁，这里会解锁
                    // 顺便提一下，只是解了object上的监视器锁，如果这个线程还持有其他对象的监视器锁，这个时候是不会释放的。
                    System.out.println(Thread.currentThread() + "等待结束");
                } catch (InterruptedException e) {
                    // do somethings
                    System.out.println(Thread.currentThread()+"抛出异常"+e.getMessage());
                }
            }
        }, "thread2");
        thread1.start();
        thread2.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            synchronized (object) { // 获取监视器锁
                thread1.interrupt();
//                object.notifyAll();
            }
        },"thread3").start();
    }
}
