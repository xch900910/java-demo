package com.example.designPattern.strategy;

public class AbstractAlarmContext {
    private AlarmStrategy alarmStrategy;

    public AbstractAlarmContext(AlarmStrategy alarmStrategy) {
        this.alarmStrategy = alarmStrategy;
    }

    public void handleAlarm() {
        this.alarmStrategy.handleAlarm();
    }
}
