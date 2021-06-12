package com.example.dubbo;

import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/6 13:47
 */
public class Consumer {

    public static void main(String[] args) {
        FileSystemXmlApplicationContext context =
                new FileSystemXmlApplicationContext(new String[]{"com/example/dubbo/dubbo-demo-consumer.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService");

        while (true) {
            try {
                Thread.sleep(1000);
                demoService.say("world");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}