package com.example.designPattern.strategy;

public class TestMain {
    public static void main(String[] args) {
        AbstractAlarmContext abstractAlarmContext = new AbstractAlarmContext(new PeopleAlarmStrategy());
        abstractAlarmContext.handleAlarm();
    }
}
