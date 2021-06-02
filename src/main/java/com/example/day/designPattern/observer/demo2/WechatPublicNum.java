package com.example.day.designPattern.observer.demo2;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-01 18:00
 **/
@Slf4j
public class WechatPublicNum {
    private static List<EventListener> listeners = new ArrayList<>();

    public void notifyListener(User user) {
        log.info("发布消息");
        //notice
        listeners.stream().forEach(x -> x.update(user));
    }

    public void register(EventListener listener) {
        listeners.add(listener);
    }

    public void unregister(EventListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }
}
