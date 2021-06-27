package com.example.testSpring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/27 10:35
 */
public class TestAop {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
        HelloService helloService = context.getBean(HelloService.class);
        helloService.say("Ryan");
    }

}
