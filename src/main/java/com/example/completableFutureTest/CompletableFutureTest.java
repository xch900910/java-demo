package com.example.completableFutureTest;

import io.netty.util.concurrent.CompleteFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-30 14:09
 **/
public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture cf = new CompletableFuture();
        cf.complete("code ending");
        System.out.println(cf.get());
    }
}
