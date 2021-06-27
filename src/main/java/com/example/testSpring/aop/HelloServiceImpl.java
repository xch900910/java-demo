package com.example.testSpring.aop;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/25 22:17
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public void say(String name) {
        System.out.println(name + " say");
//        return name + " say: He didn't let everyone down ";
    }


}
