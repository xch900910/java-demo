package com.example.nioTest.udp;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-09 17:59
 **/
public class NioUdpServer {
    public void receive() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);//设置为非阻塞
        datagramChannel.bind(new InetSocketAddress(InetAddress.getLocalHost(), 9999));
        System.out.println("服务启动成功，端口：" + 9999);
        //开启通道选择器
        Selector selector = Selector.open();
        //将通道注册在选择器上
        datagramChannel.register(selector, SelectionKey.OP_READ);
        while (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isReadable()) {
                    //去通道上面读取数据
                    datagramChannel.receive(byteBuffer);
                    byteBuffer.flip();
                    System.out.println("读取的数据：" + new String(byteBuffer.array(), 0, byteBuffer.limit()));
                    byteBuffer.clear();
                }
            }
            iterator.remove();
        }
        selector.close();
        datagramChannel.close();
    }

    public static void main(String[] args) throws IOException {
        new NioUdpServer().receive();
    }
}
