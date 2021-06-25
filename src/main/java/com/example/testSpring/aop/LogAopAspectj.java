package com.example.testSpring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/25 22:15
 */
@Aspect
@Component
public class LogAopAspectj {

    @Pointcut("execution(* com.example.testSpring.aop.HelloController.*(..))")
    public void log() {

    }

    @Before("log()")
    public void before() {
        System.out.println("before");
    }
}
