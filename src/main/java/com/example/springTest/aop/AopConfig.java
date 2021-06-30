package com.example.springTest.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/27 10:32
 */
@EnableAspectJAutoProxy
public class AopConfig {
    @Bean()
    public HelloServiceImpl helloServiceImpl() {
        return new HelloServiceImpl();
    }

    @Bean
    @Primary
    public HelloServiceImpl2 helloServiceImpl2() {
        return new HelloServiceImpl2();
    }

    @Bean
    public LogAopAspectj logAopAspectj() {
        return new LogAopAspectj();
    }
}
