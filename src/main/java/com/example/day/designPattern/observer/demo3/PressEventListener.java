package com.example.day.designPattern.observer.demo3;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-01-26 14:42
 **/
public class PressEventListener implements EventListener {
    @Override
    public void onchange(Event event) {
        System.out.println(event+"被通知");
    }
}
