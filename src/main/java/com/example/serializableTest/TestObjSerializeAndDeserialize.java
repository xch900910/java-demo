package com.example.serializableTest;

import java.io.*;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/3 18:25
 */
public class TestObjSerializeAndDeserialize {

    public static void main(String[] args) throws Exception {
        /**序列化Person对象**/
        SerializePerson();
        /**反序列Perons对象**/
        Person p = DeserializePerson();
    }

    /**
     * Description: 序列化Person对象
     */
    private static void SerializePerson() throws FileNotFoundException,
            IOException {
        Person person = new Person();
        person.setName("gacl");
        person.setAge(4000);
        person.setSex("男");
        /** ObjectOutputStream 对象输出流，将Person对象存储到E盘的Person.txt文件中，完成对Person对象的序列化操作 **/
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(
                new File("E:/Person.txt")));
        oo.writeObject(person);
        System.out.println("Person对象序列化成功！");
        oo.close();
    }

    /**
     * Description: 反序列Perons对象
     */
    private static Person DeserializePerson() throws Exception, IOException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
                new File("E:/Person.txt")));
        Person person = (Person) ois.readObject();

        System.out.println("Person对象反序列化成功！");
        System.out.println(person.toString());
        return person;
    }

}
