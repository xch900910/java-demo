package com.example.springTest.ioc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/25 21:49
 */
@Component
public class HelloService {
    @Value("${lynch.person.name}")
    private String name;


    public void say(String name) {
        System.out.println("hello," + name);
    }



    public void init() {
        System.out.println("init");
    }
}
