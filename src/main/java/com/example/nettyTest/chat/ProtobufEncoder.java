package com.example.nettyTest.chat;

import com.example.nettyTest.protobuf.MsgProtos;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/13 18:21
 */
public class ProtobufEncoder extends MessageToByteEncoder<MsgProtos.Msg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MsgProtos.Msg msg, ByteBuf out) throws Exception {
        byte[] bytes = msg.toByteArray();
        int length = bytes.length;
        //写入消息的长度
        out.writeShort(length);
        //写入魔数，版本号 todo
        out.writeBytes(bytes);


    }
}
