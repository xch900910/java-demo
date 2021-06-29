package com.example.threadPoolTest;


import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/29 7:17
 */
public class ThreadPoolExecutorDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10,
                60, TimeUnit.SECONDS, new SynchronousQueue<>());
        threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName() + " a"));
        threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName() + " b"));
        SerialExecutor serialExecutor = new SerialExecutor(threadPoolExecutor);
        serialExecutor.execute(() -> System.out.println("C"));
    }
}
