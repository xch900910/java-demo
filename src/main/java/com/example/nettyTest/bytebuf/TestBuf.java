package com.example.nettyTest.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 14:18
 */
public class TestBuf {
    public static void main(String[] args) {
        directBuffer();
//        compositeBuffer();
//        heapBuffer();
    }

    private static void heapBuffer() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.heapBuffer();
        buffer.writeBytes("比较".getBytes(StandardCharsets.UTF_8));
        if (buffer.hasArray()) {
            byte[] array = buffer.array();
            int offset = buffer.arrayOffset() + buffer.readerIndex();
            int length = buffer.readableBytes();
            System.out.println(new String(array, offset, length, StandardCharsets.UTF_8));
        }

        buffer.release();

    }

    private static void directBuffer() {
        ByteBuf directBuffer = ByteBufAllocator.DEFAULT.directBuffer();
        directBuffer.writeBytes("比较".getBytes(StandardCharsets.UTF_8));
        if (!directBuffer.hasArray()) {
            int length = directBuffer.readableBytes();
            byte[] bytes = new byte[length];
            //把数据读取到堆里
            directBuffer.readBytes(bytes);
//            directBuffer.getBytes(directBuffer.readerIndex(), bytes);
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
        }
        directBuffer.release();
    }

    private static void compositeBuffer() {
        CompositeByteBuf byteBufs = ByteBufAllocator.DEFAULT.compositeBuffer();
        ByteBuf headerBuffer = Unpooled.copiedBuffer("测试表头", StandardCharsets.UTF_8);
        ByteBuf bodyBuffer = Unpooled.copiedBuffer("测试body1", StandardCharsets.UTF_8);
        byteBufs.addComponents(headerBuffer, bodyBuffer);
        for (ByteBuf byteBuf : byteBufs) {
            int length = byteBuf.readableBytes();
            byte[] bytes = new byte[length];
            byteBuf.getBytes(byteBuf.readerIndex(), bytes);
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
        }
        headerBuffer.retain();
        byteBufs.release();
        byteBufs = ByteBufAllocator.DEFAULT.compositeBuffer();
        bodyBuffer = Unpooled.copiedBuffer("测试body2", StandardCharsets.UTF_8);
        byteBufs.addComponents(headerBuffer, bodyBuffer);
        for (ByteBuf byteBuf : byteBufs) {
            int length = byteBuf.readableBytes();
            byte[] bytes = new byte[length];
            byteBuf.getBytes(byteBuf.readerIndex(), bytes);
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
        }

        byteBufs.release();
    }
}
