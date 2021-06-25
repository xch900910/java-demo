package com.example.nettyTest.chat;

import com.example.nettyTest.protobuf.MsgProtos;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/13 18:28
 */
public class ProtobufDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        if (in.readableBytes() < 2) {
            //包头不够
            return;
        }
        short length = in.readShort();
        if (length < 0) {
            ctx.close();
        }

        if (length > in.readableBytes()) {
            //读取的消息小于消息长度，返回重新读取
            in.resetReaderIndex();
            return;
        }
        byte[] array;
        if (in.hasArray()) {
            //堆缓冲
            ByteBuf slice = in.slice();
            array = slice.array();
        } else {
            //直接缓冲
            array = new byte[length];
            in.readBytes(array, 0, length);
        }
        //字节转化成Protobuf的pojo对象
        MsgProtos.Msg msg = MsgProtos.Msg.parseFrom(array);
        if (msg != null) {
            //获取业务消息
            out.add(msg);
        }

    }
}
