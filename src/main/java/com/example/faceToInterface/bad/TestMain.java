package com.example.faceToInterface.bad;

import com.example.faceToInterface.bad.Bird;
import com.example.faceToInterface.bad.Car;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-07-15 18:18
 **/
public class TestMain {
    public static void main(String[] args) {
        Bird bird = new Bird();
        bird.work(new Car());
    }
}
