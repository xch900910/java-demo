package com.example.volatileTest;

import com.sun.javafx.logging.JFRInputEvent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-07-12 15:17
 **/
public class TestVolatile {
    private static AtomicInteger i = new AtomicInteger();
//    private static volatile i;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Task task = new Task();
        for (int j = 0; j < 2; j++) {
            new Thread(task).start();
        }

        Thread.sleep(3000);
        System.out.println("i=" + i);
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            for (int j = 0; j < 5000; j++) {
                i.incrementAndGet();
            }

        }
    }


}
