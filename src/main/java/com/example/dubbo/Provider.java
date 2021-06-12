package com.example.dubbo;

import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/6 13:47
 */
public class Provider {

    public static void main(String[] args) throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true");
        FileSystemXmlApplicationContext context =
                new FileSystemXmlApplicationContext(new String[]{"E:\\IDEA\\java-demo\\src\\main\\java\\com\\example\\dubbo\\dubbo-demo-provider.xml"});
        context.start();
        System.in.read();
    }
}
