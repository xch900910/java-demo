package com.example.nioTest.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-09 18:19
 **/
public class NioTcpClient {
    public void send() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(9999));
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);

        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isConnectable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    if (channel.isConnectionPending()) {
                        channel.finishConnect();
                    }
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("请输入：");
                    while (scanner.hasNext()) {
                        String next = scanner.next();
                        byteBuffer.put(next.getBytes());
                        byteBuffer.flip();
                        channel.write(byteBuffer);
                        byteBuffer.clear();
                    }

                    channel.register(selector, SelectionKey.OP_READ);

                }
                iterator.remove();

            }
        }
        socketChannel.close();


    }

    public static void main(String[] args) throws IOException {
        new NioTcpClient().send();
    }
}
