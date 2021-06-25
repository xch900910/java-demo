package com.example.testSpring.ioc;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/25 21:49
 */
public class HelloService {
    private String name;

    public void say(String name) {
        System.out.println("hello," + name);
    }

}
