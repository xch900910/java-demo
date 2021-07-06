package com.example.serializableTest.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.example.serializableTest.Person;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/4 10:49
 */
public class FastjsonTest {
    public static void main(String[] args) throws IOException {
        Person person = new Person();
        person.setName("gacl");
        person.setAge(4000);
        person.setSex("男");
        byte[] bytes = JSONObject.toJSONBytes(person);
        FileOutputStream outputStream = new FileOutputStream("E:/Person.txt");
        outputStream.write(bytes);
        System.out.println("Person对象序列化成功！");
        outputStream.close();
    }
}
