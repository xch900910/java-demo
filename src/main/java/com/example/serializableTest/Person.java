package com.example.serializableTest;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/3 18:24
 */

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable {
    private int age;
    private String name;
    private String sex;

}


