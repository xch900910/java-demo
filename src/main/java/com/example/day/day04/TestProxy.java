package com.example.day.day04;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.util.Arrays;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-12-15 10:09
 **/
public class TestProxy {

        public static void main(String[] args) {
            ProxyFactory proxyFactory = new ProxyFactory(new Demo());
            proxyFactory.addAdvice((MethodBeforeAdvice) (method, args1, target) -> {
                        System.out.println("你被拦截了：方法名为：" + method.getName() + " 参数为--" + Arrays.asList(args1));
                    }
            );

            Demo demo = (Demo) proxyFactory.getProxy();
            //你被拦截了：方法名为：setAge 参数为--[10]
            demo.setAge(10);

            //你被拦截了：方法名为：getAge 参数为--[]
            System.out.println(demo.getAge()); //10
            System.out.println(demo.age); //null 对你没看错，这里是null
            System.out.println(demo.findAge()); //null 对你没看错，这里是null

        }
}
