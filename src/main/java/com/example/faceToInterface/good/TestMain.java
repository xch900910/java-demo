package com.example.faceToInterface.good;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-07-15 18:24
 **/
public class TestMain {
    public static void main(String[] args) {
        Person person = new Person();
        person.work(new Car());
        person.work(new Foot());
    }

}
