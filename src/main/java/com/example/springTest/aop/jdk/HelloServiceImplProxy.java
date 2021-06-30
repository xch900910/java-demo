package com.example.springTest.aop.jdk;

import com.example.springTest.aop.HelloService;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/27 21:35
 */
@Slf4j
public class HelloServiceImplProxy {
    private HelloService helloService;

    public HelloServiceImplProxy(HelloService helloService) {
        this.helloService = helloService;
    }

    public HelloService getProxy() {
        return (HelloService) Proxy.newProxyInstance(HelloServiceImplProxy.class.getClassLoader(), new Class[]{HelloService.class}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("say".equals(method.getName())) {
                    log.info("说话前处理");
                    method.invoke(helloService, args);
                    log.info("说话后处理");
                }
                return proxy;
            }
        });
    }


}
