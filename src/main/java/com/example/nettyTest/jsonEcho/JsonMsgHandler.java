package com.example.nettyTest.jsonEcho;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

//服务器端业务处理器
@Slf4j
public class JsonMsgHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String json = (String) msg;
        JsonMsg jsonMsg = JsonMsg.parseFromJson(json);
        log.info("收到一个 Json 数据包 =》" + jsonMsg);

    }
}