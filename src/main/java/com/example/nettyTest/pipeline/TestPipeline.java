package com.example.nettyTest.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 12:05
 */
public class TestPipeline {
    public static void main(String[] args) throws InterruptedException {
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addFirst(new SimpleHandlerB());
                ch.pipeline().addFirst(new SimpleHandlerC());
                ch.pipeline().addFirst(new SimpleHandlerA());
            }
        });
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(1);
        embeddedChannel.writeInbound(buffer);
        embeddedChannel.flush();
        embeddedChannel.close();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
