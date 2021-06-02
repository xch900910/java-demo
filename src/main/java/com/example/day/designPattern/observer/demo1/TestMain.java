package com.example.day.designPattern.observer.demo1;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-01 18:07
 **/
public class TestMain {
    public static void main(String[] args) {
        User aaa = new User("aaa");
        User bbb = new User("bbb");
        User ccc = new User("ccc");
        WechatPublicNum wechatPublicNum = new WechatPublicNum();
        wechatPublicNum.sub(aaa);
        wechatPublicNum.sub(bbb);
        wechatPublicNum.sub(ccc);
        wechatPublicNum.unsub(aaa);
        wechatPublicNum.publishMsg();
    }
}
