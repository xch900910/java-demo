package com.example.testSpring.aop.cglib;

import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/27 22:10
 */
//方法拦截器 会拦截 所有方法 ，所以需要加判断  cglib 还提供了filter过滤器 可以用于过滤指定方法
public class TransactionInterceptor implements MethodInterceptor {

    TransactionManager tx = new TransactionManager();

    @Override
    public Object intercept(Object o, Method method, Object[] objects, net.sf.cglib.proxy.MethodProxy methodProxy) throws Throwable {
        tx.start();
        Object value = methodProxy.invokeSuper(o, objects);
        tx.commit();
        return value;
    }
}
