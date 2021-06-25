package com.example.ListTest;

import java.util.ArrayList;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-24 18:20
 **/
public class TestArrayList {
    public static void main(String[] args) {
        ArrayList<Person> arrayList = new ArrayList<>();

        for (int i = 0; i < 101000000; i++) {
            Person person = new Person("name" + i, i + 1);
            arrayList.add(person);
        }
    }
}
