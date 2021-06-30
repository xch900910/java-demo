package com.example.springTest.aop.cglib;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/28 7:19
 */
public class WorkServiceImpl {

    public String doWork(String string) {
        System.out.println(string + "is wording");
        return string;
    }
}
