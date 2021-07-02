package com.example.completableFutureTest;

import com.example.nettyTest.jsonEcho.RandomUtil;

import java.util.concurrent.CompletableFuture;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/2 17:28
 */
public class CompletableFutureTest2 {
    public static void main(String[] args) {
        CompletableFuture<Void> task1 =
                CompletableFuture.supplyAsync(() -> {
                    //自定义业务操作
                    System.out.println("do custom sync begin ");
                    try {
                        Thread.sleep(RandomUtil.randInMod(10) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("do custom sync end ");
                    return null;
                });

        CompletableFuture<Void> task2 =
                CompletableFuture.supplyAsync(() -> {
                    //自定义业务操作
                    System.out.println("do supplier sync begin ");
                    try {
                        Thread.sleep(RandomUtil.randInMod(10) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("do supplier sync end ");
                    return null;
                });

        CompletableFuture<Void> task3 =
                CompletableFuture.supplyAsync(() -> {
                    //自定义业务操作
                    System.out.println("do business sync begin");
                    try {
                        Thread.sleep(RandomUtil.randInMod(10) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("do business  sync end ");
                    return null;
                });

        CompletableFuture<Void> task4 =
                CompletableFuture.supplyAsync(() -> {
                    //自定义业务操作
                    System.out.println("do warehouse  sync begin ");
                    try {
                        Thread.sleep(RandomUtil.randInMod(10) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("do warehouse  sync end  ");
                    return null;
                });

        CompletableFuture<Void> headerFuture = CompletableFuture.allOf(task1, task2, task3, task4);

        try {
            headerFuture.join();
        } catch (Exception ex) {

        }
        System.out.println("all done. ");
    }
}
