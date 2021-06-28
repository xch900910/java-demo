package com.example.threadTest.demo1;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/29 6:55
 */

/**
 * @description: join会释放锁
 * @author: Leesin.Dong
 * @date: Created in 2020/3/18 22:50
 * @version:
 * @modified By:
 */
public class JoinReleaseLock {

    public static void main(String[] args) {
        ThreadB b = new ThreadB();
        ThreadA a = new ThreadA(b);
        ThreadC c = new ThreadC(b);
        c.start();
        a.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    static class ThreadA extends Thread {
        private ThreadB b;

        public ThreadA(ThreadB b) {
            super();
            this.b = b;
        }

        @Override
        public void run() {
            System.out.println("a enter run");
            try {
                synchronized (b) {
                    System.out.println("a obtain b锁");
                    b.start();
                    b.join();//执行join()方法的一瞬间，b锁立即释放。
                    System.out.println("a obtain lock again");
                    for (int i = 0; i < 5; i++) {
                        String newString = new String();
                        Math.random();

                    }
                    System.out.println("a release b锁");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThreadB extends Thread {

        @Override
        public void run() {
            try {
                System.out.println("b run begin timer=" + System.currentTimeMillis());
                Thread.sleep(1000);
                System.out.println("b run begin timer=" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized public void bService() {
            System.out.println(" c obtain b锁");
            System.out.println("打印了BService time = " + System.currentTimeMillis());
            System.out.println(" c release b锁");
        }
    }

    static class ThreadC extends Thread {

        private ThreadB threadB;

        public ThreadC(ThreadB threadB) {
            super();
            this.threadB = threadB;
        }

        @Override
        public void run() {
            System.out.println("c enter run");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadB.bService();

        }
    }


}

