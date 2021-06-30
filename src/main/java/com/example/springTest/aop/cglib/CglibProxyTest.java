package com.example.springTest.aop.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/27 22:09
 */
public class CglibProxyTest {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(WorkServiceImpl.class);
        // NoOp.INSTANCE 表示没有被匹配的方法不执行任何操作 如果CallbackFilter返回0
        //代表使用callbacks集合中的第一个元素执行方法拦截策略 1 则使用第二个。
        Callback[] callbacks = {new TransactionInterceptor(), NoOp.INSTANCE};
        //设置回调函数集合
        enhancer.setCallbacks(callbacks);
        //根据回调过滤器返回指定回调函数索引
        enhancer.setCallbackFilter(new TransactionFilter());
        WorkServiceImpl workService = (WorkServiceImpl) enhancer.create();
        workService.doWork("Ryan");


    }
}
