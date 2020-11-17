package com.example.day.day02;

import java.util.concurrent.*;

/**
 * @author xch900910
 * @version 1.0
 * @Desc
 * @Date 2020/11/15 11:29
 **/
public class FutureDemo implements  Callable<String> {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newFixedThreadPool(2);
        FutureTask<String> futureTask = new FutureTask<String>(new FutureDemo());
//        Future future=  executorService.submit(futureTask);
        futureTask.run();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public String call() throws Exception {
        System.out.println("start");
        Thread.sleep(2000);
        System.out.println("sleep end");
        return "s";
    }


}
