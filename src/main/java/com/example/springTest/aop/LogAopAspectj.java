package com.example.springTest.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/25 22:15
 */
@Aspect
public class LogAopAspectj {

    @Pointcut("execution(public * com.example.springTest.aop.HelloServiceImpl2.*(..))")
    public void log() {

    }

    @Before("log()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        System.out.println("this:" + joinPoint.getThis());
        System.out.println("target:" + joinPoint.getTarget());
        System.out.println("kind:" + joinPoint.getKind());
        System.out.println("sign:" + joinPoint.getSignature());
        System.out.println("args:" + joinPoint.getArgs());
        System.out.println("sourcelocation:" + joinPoint.getSourceLocation());
        System.out.println("staticpart:" + joinPoint.getStaticPart());
        System.out.println("" + joinPoint.getSignature().getName() + "运行。。" +
                "。@Before:参数列表是：{" + Arrays.asList(args) + "}");
    }

    @After("log()")
    public void after(JoinPoint joinPoint) {
        System.out.println(joinPoint.getSignature().getName() + "结束");
    }

    @Around("log()")
    public void Around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @AfterReturning(value = "log()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println(joinPoint.getSignature().getName() + "正常返回：" + result);

    }

    @AfterThrowing(value = "log()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        System.out.println(joinPoint.getSignature().getName() + "抛出异常：" + exception);
    }
}
