package com.example.nioTest;

import java.nio.IntBuffer;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-09 17:29
 **/
public class BufferTest {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(20);
        System.out.println("初始化时limit=" + intBuffer.limit());
        System.out.println("初始化时capacity=" + intBuffer.capacity());
        System.out.println("初始化时position=" + intBuffer.position());
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        System.out.println("插入5个元数后limit=" + intBuffer.limit());
        System.out.println("插入5个元数后capacity=" + intBuffer.capacity());
        System.out.println("插入5个元数后position=" + intBuffer.position());
        //插入后读取要切换
        intBuffer.flip();

        for (int i = 0; i < 2; i++) {
            System.out.println("===========" + intBuffer.get() + "============");
        }

        System.out.println("读取2个后limit=" + intBuffer.limit());
        System.out.println("读取2个后capacity=" + intBuffer.capacity());
        System.out.println("读取2个后position=" + intBuffer.position());
        for (int i = 0; i < 3; i++) {
            System.out.println("===========" + intBuffer.get() + "============");
        }

        System.out.println("再读取3个后limit=" + intBuffer.limit());
        System.out.println("再读取3个后capacity=" + intBuffer.capacity());
        System.out.println("再读取3个后position=" + intBuffer.position());
        //intBuffer.get();  //会抛出 java.nio.BufferUnderflowException
        intBuffer.clear();
        for (int i = 0; i < 2; i++) {
            intBuffer.put(i);
        }

        System.out.println("读取完成后再重新放limit=" + intBuffer.limit());
        System.out.println("读取完成后再重新放capacity=" + intBuffer.capacity());
        System.out.println("读取完成后再重新放position=" + intBuffer.position());
    }
}
