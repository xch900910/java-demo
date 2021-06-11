package com.example.nioTest;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-10 09:37
 **/
@Slf4j
public class FutureTaskTest {
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

    public static boolean drinkTea(boolean waterOk, boolean cupOk) {
        if (!waterOk) {
            log.info("烧水失败");
            return false;
        }
        if (!cupOk) {
            log.info("清洗失败");
            return false;
        }
        log.info("泡茶成功");
        return true;
    }

    public static void main(String[] args) {
        Callable<Boolean> washCupJob = new WashCupJob();
        FutureTask<Boolean> wtask = new FutureTask<>(washCupJob);
        Thread wthread = new Thread(wtask);
        wthread.setName("washCup");
        wthread.start();
        Callable<Boolean> hotWaterJob = new HotWaterJob();
        FutureTask<Boolean> htask = new FutureTask<>(hotWaterJob);
        Thread hthread = new Thread(htask);
        hthread.setName("hotWater");
        hthread.start();
        Thread.currentThread().setName("main");

        try {
            Boolean wOK = wtask.get();
            Boolean hOK = htask.get();
            drinkTea(hOK, wOK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        log.info("end");


    }
}
