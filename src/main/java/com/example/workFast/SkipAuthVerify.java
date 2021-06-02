package com.example.workFast;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: xiongchaohua
 * @Des : 跳过接口认证
 * @create: 2020-07-21 19:24
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SkipAuthVerify {
    boolean required() default true;
}
