package com.example.springTest.ioc;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/26 13:34
 */
public class BService {
    private HelloService helloService;

    public void bSay() {
        System.out.println("bsay");
    }

    public void setHelloService(AService helloService) {
    }
}
