package com.example.threadTest.demo1;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/28 21:26
 */
public class JoinDemo1 {
    class Thread1 extends Thread {
        Thread2 thread2;

        public Thread1(Thread2 thread2) {
            this.thread2 = thread2;
        }

        @Override
        public void run() {
            System.out.println("thread1 enter,this:" + this.getName());
            synchronized (thread2) {
                long start = System.currentTimeMillis();
                try {
                    System.out.println("开始释放锁");
                    thread2.wait();
                    System.out.println("重新获取锁，cost：" + (System.currentTimeMillis() - start) / 1000 + "s");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (this) {
                System.out.println("thread2 enter,this:" + this.getName());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void main(String[] args) {
        JoinDemo1 joinDemo1 = new JoinDemo1();
        Thread2 thread2 = joinDemo1.new Thread2();
        Thread1 thread1 = joinDemo1.new Thread1(thread2);
        thread1.start();
        thread2.start();
    }
}
