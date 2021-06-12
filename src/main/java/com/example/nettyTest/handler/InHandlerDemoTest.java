package com.example.nettyTest.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 10:15
 */
public class InHandlerDemoTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("===========================================");
        InHandlerDemo inHandlerDemo = new InHandlerDemo();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(inHandlerDemo);
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
