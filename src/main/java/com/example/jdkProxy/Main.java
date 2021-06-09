package com.example.jdkProxy;

import java.lang.reflect.Proxy;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-09 09:00
 **/
public class Main {
    public static void main(String[] args) {
        People people = new Person("zhuoaa");
        JdkInvocationHandler<People> peopleJdkInvocationHandler = new JdkInvocationHandler<>(people);
        People o = (People) Proxy.newProxyInstance(people.getClass().getClassLoader(), new Class<?>[]{People.class}, peopleJdkInvocationHandler);
        o.say();
        System.out.println(o.toString());

    }
}
