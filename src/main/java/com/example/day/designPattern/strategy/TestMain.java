package com.example.day.designPattern.strategy;

public class TestMain {
    public static void main(String[] args) {
        AbstractAlarmContext abstractAlarmContext = new AbstractAlarmContext(new PeopleAlarmStrategy());
        abstractAlarmContext.handleAlarm();
    }
}
