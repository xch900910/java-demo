package com.example.testSpring.ioc;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/25 21:49
 */
public class HelloService {
    private String name;
    private AService aService;

    public void say(String name) {
        System.out.println("hello," + name);
    }

    public void setaService(AService aService) {
        this.aService = aService;
    }

    public AService getaService() {
        return aService;
    }

    public void init() {
        System.out.println("init");
    }
}
