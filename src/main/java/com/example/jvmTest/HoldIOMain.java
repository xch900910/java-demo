package com.example.jvmTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/23 21:49
 */
public class HoldIOMain {
    public static class HoldIOTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    FileOutputStream fos = new FileOutputStream(new File("temp"));
                    for (int i = 0; i < 10000; i++) {
                        fos.write(i);
                        fos.close();
                        FileInputStream fis = new FileInputStream(new File("temp"));
                        while (fis.read() != -1) ; //a log of read operations.
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public static class LazyTask implements Runnable {
        public void run() {
            try {
                while (true) {
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
            }
        }

    }

    public static void main(String[] args) {    //四线程操作
        new Thread(new HoldIOTask()).start();
        new Thread(new LazyTask()).start();
        new Thread(new LazyTask()).start();
        new Thread(new LazyTask()).start();
    }
}

