package com.example.threadPoolTest;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/6 7:20
 */
public class TestWorker {
    public static void main(String[] args) {
        Worker first_task = new Worker(() -> System.out.println("first task"));
        first_task.thread.start();
    }
}
