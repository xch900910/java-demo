package com.example.socketTest.demo3;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-05-24 16:50
 **/
@Slf4j
public class NioSocketServer {
    private static int port = 9000;
    private ServerSocketChannel serverSocketChannel;
    private Charset charset = Charset.forName("UTF-8");
    private Selector selector = null;

    public NioSocketServer() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        log.info("server start on port:{}", port);
    }

    public void service() throws IOException {
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0) {
            Iterator iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    System.out.println("客户端机子的地址是 "
                            + sc.socket().getRemoteSocketAddress()
                            + "  客户端机机子的端口号是 "
                            + sc.socket().getLocalPort());
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                }
                if (key.isReadable()) {
                    readMsg(key);
                }
                if (key.isWritable()) {
                    writeMsg(key);
                }
            }
        }
    }

    private void writeMsg(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        InputStream in = System.in;
        byte[] b = new byte[1024];
        int read = in.read(b);
        String string = new String(b, 0, read);
        socketChannel.write(ByteBuffer.wrap(string.getBytes()));
    }

    private void readMsg(SelectionKey key) throws IOException {
        if (key == null)
            return;

        //***用channel.read()获取客户端消息***//
        //：接收时需要考虑字节长度
        SocketChannel sc = (SocketChannel) key.channel();
        String content = "";
        ByteBuffer buf = ByteBuffer.allocate(128);
        int bytesRead = sc.read(buf); //read into buffer.

        while (bytesRead > 0) {
            buf.flip();  //make buffer ready for read
            content += charset.decode(buf);
            buf.clear(); //make buffer ready for writing
            bytesRead = sc.read(buf);
        }
        System.out.println("server接收：" + content.trim());
    }

    public static void main(String[] args) throws IOException {
        new NioSocketServer().service();
    }
}
