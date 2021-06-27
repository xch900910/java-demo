package com.example.testSpring.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/25 21:48
 */
public class TestIoc {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        HelloService helloService = (HelloService) context.getBean("helloService");
        helloService.say("world");
//        AService aService = (AService) context.getBean("aService");
//        aService.aSay();
//        BService bService = (BService) context.getBean("bService");
//        bService.bSay();
    }

}
