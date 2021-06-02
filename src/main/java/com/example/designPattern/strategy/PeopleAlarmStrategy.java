package com.example.designPattern.strategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PeopleAlarmStrategy implements AlarmStrategy {
    @Override
    public void handleAlarm() {
        log.info("======handle people alarm======");
    }
}
