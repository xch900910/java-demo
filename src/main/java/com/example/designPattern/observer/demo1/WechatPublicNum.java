package com.example.designPattern.observer.demo1;

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
    private static List<User> users = new ArrayList<>();

    public void publishMsg() {
        log.info("发布消息");
        //notice
        users.stream().forEach(x -> x.update());
    }

    public void sub(User user) {
        users.add(user);
        log.info(user.getName() + ":订阅成功");

    }

    public void unsub(User user) {
        if (users.contains(user)) {
            users.remove(user);
            log.info(user.getName() + ":取消订阅成功");
        }
    }
}
