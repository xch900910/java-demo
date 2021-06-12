package com.example.designPattern.chain;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 11:15
 */
public abstract class AbstractHandler implements Handler {
    public AbstractHandler(String name) {
        this.name = name;
    }

    protected String name;
    protected Handler handler;

    public void setNextHandler(Handler handler) {
        this.handler = handler;
    }
}
