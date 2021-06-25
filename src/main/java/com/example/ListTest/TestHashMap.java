package com.example.ListTest;

import java.util.HashMap;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-24 18:50
 **/
public class TestHashMap {
    public static void main(String[] args) {
        HashMap<String, Person> objectObjectHashMap = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {
            objectObjectHashMap.put(i + "", new Person("name" + i, i));
        }
        System.out.println(objectObjectHashMap.get("0"));
        System.out.println(objectObjectHashMap.size());
    }
}
