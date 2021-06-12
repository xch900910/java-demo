package com.example.nettyTest.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 22:12
 */
public class Byte2IntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 4) {
            Long readInt = in.readLong();
            System.out.println("解码出整数：" + readInt);
            out.add(readInt);
        }
    }
}
