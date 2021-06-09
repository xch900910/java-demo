package com.example.nioTest.udp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-09 17:59
 **/
public class NioUdpClient {
    public void send() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入发送内容：");
        while (scanner.hasNext()) {
            String next = scanner.next();
            byteBuffer.put(next.getBytes());
            byteBuffer.flip();
            //通过通道发送数据
            datagramChannel.send(byteBuffer, new InetSocketAddress(InetAddress.getLocalHost(), 9999));
            byteBuffer.clear();
        }
        datagramChannel.close();
    }

    public static void main(String[] args) throws IOException {
        new NioUdpClient().send();
    }
}
