package com.example.threadPoolTest;

import java.util.concurrent.Executors;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/6 7:19
 */
public class Worker implements Runnable {
    final Thread thread;
    Runnable firstTask;

    public Worker(Runnable firstTask) {
        this.firstTask = firstTask;
        this.thread = Executors.defaultThreadFactory().newThread(this);
    }

    @Override
    public void run() {
        System.out.println("worker run by start");
        runWorker(this);
    }

    private void runWorker(Worker worker) {
        Runnable task = worker.firstTask;
        task.run();
    }


}
