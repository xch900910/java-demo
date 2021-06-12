package com.example.nettyTest.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 22:25
 */
public class TestDecode {
    public static void main(String[] args) {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new Byte2IntegerDecoder());
                ch.pipeline().addLast(new IntegerProcessHandler());
            }
        });

        for (Long i = 0L; i < 100; i++) {
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeLong(i);
            embeddedChannel.writeInbound(buffer);
        }
    }
}
