package com.example.nettyTest.jsonEcho;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/13 14:31
 */
public class JsonClient {
    static String content = "{\"content\":\"疯狂创客圈-Java高并发社群\",\"id\":10}";
    private int port;

    public JsonClient(int port) {
        this.port = port;
    }

    private void start() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.channel(NioSocketChannel.class).group(group)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 客户端channel流水线添加2个handler处理器
                        ch.pipeline().addLast(new LengthFieldPrepender(4));
                        ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(port)).sync();
            Channel channel = channelFuture.channel();
            for (int i = 0; i < 1000; i++) {
                byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        new JsonClient(9999).start();
    }
}
