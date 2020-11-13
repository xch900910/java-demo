package com.example.day.day01;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-11-13 18:28
 **/
public class ThreadDemo {
    public static void main(String[] args) {
         Object object = new Object();
        new Thread(()->{
            synchronized (object) { // 获取监视器锁
                try {
                    System.out.println(Thread.currentThread()+"开始等待");
                    object.wait(); // 这里会解锁，这里会解锁，这里会解锁
                    // 顺便提一下，只是解了object上的监视器锁，如果这个线程还持有其他对象的监视器锁，这个时候是不会释放的。
                    System.out.println(Thread.currentThread()+"等待后续");
                } catch (InterruptedException e) {
                    // do somethings
                }
            }
        },"thread1").start();

        new Thread(()->{
            synchronized (object) { // 获取监视器锁
                try {
                    System.out.println(Thread.currentThread()+"开始等待");
                    object.wait(); // 这里会解锁，这里会解锁，这里会解锁
                    // 顺便提一下，只是解了object上的监视器锁，如果这个线程还持有其他对象的监视器锁，这个时候是不会释放的。
                    System.out.println(Thread.currentThread()+"等待后续");
                } catch (InterruptedException e) {
                    // do somethings
                }
            }
        },"thread2").start();


    }
}
