package com.example.serializableTest.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.example.serializableTest.Person;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/4 10:49
 */
public class FastjsonTest {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("gacl");
        person.setAge(4000);
        person.setSex("男");
        System.out.println(JSONObject.toJSONString(person));
    }
}
