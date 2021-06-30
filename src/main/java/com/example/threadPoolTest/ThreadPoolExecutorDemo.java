package com.example.threadPoolTest;


import com.example.workNote.ServiceException;

import java.util.concurrent.LinkedBlockingDeque;
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
                60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(20));
        System.out.println("after init  activeCount:" + threadPoolExecutor.getActiveCount());
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            threadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("dfd:" + finalI);
                if (finalI == 2) {
                    throw new ServiceException("dfdf");
                }

            });
        }

        System.out.println("after execute activeCount:" + threadPoolExecutor.getActiveCount());
        System.out.println("after execute queueCount:" + threadPoolExecutor.getQueue().size());

    }
}
