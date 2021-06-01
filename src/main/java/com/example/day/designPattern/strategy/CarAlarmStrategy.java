package com.example.day.designPattern.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarAlarmStrategy implements AlarmStrategy {
    @Override
    public void handleAlarm() {
        log.info("======handle car alarm======");
    }
}
