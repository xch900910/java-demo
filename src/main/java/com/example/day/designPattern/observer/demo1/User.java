package com.example.day.designPattern.observer.demo1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-01 18:00
 **/
@Slf4j
@Data
@AllArgsConstructor
public class User {
    private String name;

    public void update() {
        log.info(name + "知道wechat 发布通知了");
    }
}
