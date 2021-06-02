package com.example.day.designPattern.observer.demo2;


/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-01 18:42
 **/
public class TestMain {
    public static void main(String[] args) {
        User aaa = new User("aaa");
        WechatPublicNum wechatPublicNum = new WechatPublicNum();
        EventListener eventListener = new EventListenerImpl();
        wechatPublicNum.register(eventListener);
        wechatPublicNum.notifyListener(aaa);
    }
}
