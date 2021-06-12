package com.example.nettyTest.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 15:18
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        int length = byteBuf.readableBytes();
        byte[] bytes = new byte[length];
        byteBuf.getBytes(0, bytes);
        System.out.println("server receive :" + new String(bytes, StandardCharsets.UTF_8));
        System.out.println("写回前：msg.refCnt" + ((ByteBuf) msg).refCnt());
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeBytes(bytes);
        ChannelFuture channelFuture = ctx.writeAndFlush(buffer);
        channelFuture.addListener(future -> System.out.println("写回后：msg.refCnt" + ((ByteBuf) msg).refCnt()));
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
