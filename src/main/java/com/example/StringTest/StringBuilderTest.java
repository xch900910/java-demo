package com.example.StringTest;

import java.util.HashSet;
import java.util.Iterator;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-07-06 09:45
 **/
public class StringBuilderTest {
    public static void main(String[] args) {
        HashSet<StringBuilder> hs = new HashSet<StringBuilder>();
        StringBuilder sb1 = new StringBuilder("aaa");
        StringBuilder sb2 = new StringBuilder("aaabbb");
        hs.add(sb1);
        hs.add(sb2);    //这时候HashSet里是{"aaa","aaabbb"}
        StringBuilder sb3 = sb1;
        sb3.append("bbb");  //这时候HashSet里是{"aaabbb","aaabbb"}
        System.out.println(hs);
        System.out.println(hs.size());
        Iterator<StringBuilder> iterator = hs.iterator();
        while (iterator.hasNext()) {
            System.out.println("key:=" + iterator.next());
        }
    }
}
