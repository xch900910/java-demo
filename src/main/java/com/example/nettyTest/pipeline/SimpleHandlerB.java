package com.example.nettyTest.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 10:59
 */
@Slf4j
public class SimpleHandlerB extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("B channelRead");
        super.channelRead(ctx, msg);
    }
}
