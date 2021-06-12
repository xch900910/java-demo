package com.example.nettyTest.decode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 22:23
 */
public class IntegerProcessHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Long integer = (Long) msg;
        System.out.println("读取到整数：" + integer);
        super.channelRead(ctx, msg);
    }
}
