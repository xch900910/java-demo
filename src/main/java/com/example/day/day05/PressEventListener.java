package com.example.day.day05;

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
