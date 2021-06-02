package com.example.day.designPattern.observer.demo3;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-01-26 14:16
 **/
@Data
public class EventSource {
    private Event event;
    private List<EventListener> listeners = Lists.newArrayList();
    public void addListener(EventListener eventListener) {
        this.listeners.add(eventListener);
    }
    public void press(){
        System.out.println("当前事件源发生");
        this.listeners.stream().forEach(x->x.onchange(event));
    }
}
