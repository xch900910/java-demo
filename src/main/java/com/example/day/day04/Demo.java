package com.example.day.day04;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-12-15 10:10
 **/
public class Demo {
    public Integer age;

    // 此处用final修饰了  CGLIB也不会代理此方法了
    public final Integer findAge() {
        return age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
