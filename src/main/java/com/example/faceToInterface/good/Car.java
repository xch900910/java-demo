package com.example.faceToInterface.good;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-07-15 18:21
 **/
public class Car implements Move {
    @Override
    public void work() {
        System.out.println("move by car");
    }
}
