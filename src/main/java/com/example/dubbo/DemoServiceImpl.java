package com.example.dubbo;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/6 13:49
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public void say(String word) {
        System.out.println("hello " + word);
    }
}
