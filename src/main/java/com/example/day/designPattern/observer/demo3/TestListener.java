package com.example.day.designPattern.observer.demo3;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-01-26 14:26
 **/
public class TestListener {
    public static void main(String[] args) {
        EventSource eventSource = new EventSource();
        eventSource.setEvent(new Event("test"));
        eventSource.addListener(new EventListener() {
            @Override
            public void onchange(Event event) {
                System.out.println(event +"被通知");
            }
        });
        eventSource.press();
    }
}
