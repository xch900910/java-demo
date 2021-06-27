package com.example.testSpring.aop.cglib;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/28 6:55
 */
@Slf4j
public class TransactionManager {
    public void start() {
        log.info("start tx");
//        MessageTracerUtils.addMessage("start tx");
    }

    public void commit() {
        log.info("commit tx");
//        MessageTracerUtils.addMessage("commit tx");
    }

    public void rollback() {
        log.info("rollback tx");
//        MessageTracerUtils.addMessage("rollback tx");
    }

    public Object getAspectInstance() {
        return new TransactionManager();
    }
}
