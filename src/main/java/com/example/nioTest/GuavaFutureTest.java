package com.example.nioTest;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-10 10:26
 **/
@Slf4j
public class GuavaFutureTest {
    public static String getCurrentThread() {
        return Thread.currentThread().getName();
    }

    static class HotWaterJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            log.info("开始烧水");
            Thread.sleep(3000);
            log.info("水烧好了");
            return true;
        }
    }

    static class WashCupJob implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            log.info("开始清洗水杯");
            Thread.sleep(3000);
            log.info("洗好了");
            return true;
        }
    }

    static class DrinkTeaJob implements Runnable {
        boolean washOK = false;
        boolean hotOk = false;


        @Override
        public void run() {
            while (true) {
                log.info("读取中");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (hotOk && washOK) {
                    drinkTea(hotOk, washOK);
                }
            }


        }

        public boolean drinkTea(boolean waterOk, boolean cupOk) {
            if (!waterOk) {
                log.info("烧水失败");
                return false;
            }
            if (!cupOk) {
                log.info("清洗失败");
                return false;
            }
            log.info("泡茶成功");
            this.washOK = false;
            this.hotOk = false;
            return true;
        }
    }

    public static void main(String[] args) {
        DrinkTeaJob drinkTeaJob = new DrinkTeaJob();
        Thread mainThread = new Thread(drinkTeaJob);
        mainThread.setName("主线程");
        mainThread.start();
        Callable<Boolean> washCupJob = new FutureTaskTest.WashCupJob();
        Callable<Boolean> hotWaterJob = new FutureTaskTest.HotWaterJob();
        ExecutorService threadPool = Executors.newFixedThreadPool(11);
        ListeningExecutorService listeningDecorator = MoreExecutors.listeningDecorator(threadPool);
        ListenableFuture<Boolean> washFuture = listeningDecorator.submit(washCupJob);
        Futures.addCallback(washFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean result) {
                if (result) {
                    drinkTeaJob.washOK = true;
                    log.info("清洁成功");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log.info("清洁失败");
            }
        }, listeningDecorator);
        ListenableFuture<Boolean> hotFuture = listeningDecorator.submit(hotWaterJob);
        Futures.addCallback(hotFuture, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@Nullable Boolean result) {
                if (result) {
                    drinkTeaJob.hotOk = true;
                    log.info("烧水成功");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log.info("烧水失败");
            }
        }, listeningDecorator);

        log.info("end");


    }
}
