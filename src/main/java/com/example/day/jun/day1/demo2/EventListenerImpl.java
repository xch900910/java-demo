package com.example.day.jun.day1.demo2;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-01 18:44
 **/
public class EventListenerImpl implements EventListener {
    @Override
    public void update(User user) {
        System.out.println(user.getName() + "被通知发布消息了");
    }
}
