package com.example.testSpring.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/27 10:32
 */
@EnableAspectJAutoProxy
public class AopConfig {
    @Bean
    public HelloService helloController() {
        return new HelloServiceImpl();
    }

    @Bean
    public LogAopAspectj logAopAspectj() {
        return new LogAopAspectj();
    }
}
