package com.example.classInit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-22 09:41
 **/
public class TestSerial {
//        public static void main(String[] args) throws IOException {
//            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//            try (ObjectOutputStream output = new ObjectOutputStream(buffer)) {
//                // 写入int:
//                output.writeInt(12345);
//                // 写入String:
//                output.writeUTF("Hello");
//                // 写入Object:
//                output.writeObject(Double.valueOf(123.456));
//            }
//            System.out.println(Arrays.toString(buffer.toByteArray()));
//        }

    public static void main(String[] args) {
        System.out.println((byte) '\n');
        System.out.println((byte) '中');
    }
}
