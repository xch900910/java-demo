package com.example.nioTest.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-09 18:18
 **/
public class NioTcpServer {
    public void receive() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9999));
        System.out.println("服务器启动，端口：" + 9999);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int length = -1;
                    while ((length = channel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println("服务器收到：" + new String(byteBuffer.array(), 0, byteBuffer.limit()));
                        byteBuffer.clear();
                    }
                    channel.close();
                }
                //移除key
                iterator.remove();
            }
        }
        //关闭连接
        serverSocketChannel.close();

    }

    public static void main(String[] args) throws IOException {
        new NioTcpServer().receive();
    }
}
