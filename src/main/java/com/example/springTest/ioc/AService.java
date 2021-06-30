package com.example.springTest.ioc;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/26 13:33
 */

public class AService {
    private BService bService;

    public void aSay() {
        System.out.println("asay");
    }

    public AService(BService bService) {
        this.bService = bService;
    }
}
