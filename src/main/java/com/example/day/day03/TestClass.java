package com.example.day.day03;

import sun.misc.Launcher;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-12-14 15:31
 **/
public class TestClass {
    public static void main(String[] args) {
//        URL[] urls = ((URLClassLoader)ClassLoader.getSystemClassLoader()).getURLs();
//        for (URL url : urls) {
//            System.out.println(url);
//        }

        System.out.println(System.getProperty("java.ext.dirs"));

    }
// file:/D:/Workbench/Test/bin/
}
