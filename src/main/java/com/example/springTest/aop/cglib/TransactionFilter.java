package com.example.springTest.aop.cglib;


import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/28 6:57
 */
public class TransactionFilter implements CallbackFilter {
    //返回回调函数在集合中的索引
    public int accept(Method method) {
        if ("doWork".equals(method.getName())) {
            return 0;
        } else {
            return 1;
        }
    }
}
