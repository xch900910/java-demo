package com.example.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-09 09:41
 **/
public class JdkInvocationHandler<T> implements InvocationHandler {
    T target;

    public JdkInvocationHandler(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("调用方法前");
        Object invoke = method.invoke(target, args);
        System.out.println("调用方法后");

        return invoke;
    }
}
