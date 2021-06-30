package com.example.springTest.aop;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/28 6:59
 */
public class HelloServiceImpl2 implements HelloService {
    @Override
    public void say(String name) {
        System.out.println(name + " say2");
    }
}
