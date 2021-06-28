package com.example.threadTest.demo1;

import static java.lang.System.out;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/28 20:57
 */
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {
        JoinDemo joinDemo = new JoinDemo();
        TimerThread timerThread = joinDemo.new TimerThread();
        timerThread.start();
        synchronized (timerThread) {
            timerThread.wait();
        }
//        timerThread.join();
        out.println(Thread.currentThread().getName() + " is end");
    }

    class TimerThread extends Thread {
        @Override
        public void run() {
            out.println(Thread.currentThread().getName() + " is running");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println(Thread.currentThread().getName() + " is end");
        }
    }
}
