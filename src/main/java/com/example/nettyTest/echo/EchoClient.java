package com.example.nettyTest.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/12 15:29
 */
public class EchoClient {
    private int port;

    public EchoClient(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new EchoClient(9999).start();
    }

    private void start() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new EchoClientHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(port)).sync();
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("客户端连接成功");
                    } else {
                        System.out.println("客户端连接失败");
                    }
                }
            });
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入内容");
            while (scanner.hasNext()) {

                String next = scanner.next();
                byte[] bytes = next.getBytes(StandardCharsets.UTF_8);
                ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
