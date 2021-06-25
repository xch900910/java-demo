package com.example.testSpring.aop;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/25 22:17
 */
@RestController("/")
public class HelloController {

    @RequestMapping("/index")
    public void index() {
        System.out.println("index");
    }
}
