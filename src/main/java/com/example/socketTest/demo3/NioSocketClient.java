package com.example.socketTest.demo3;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-05-24 16:50
 **/
@Slf4j
public class NioSocketClient {

    private Selector selector;
    private SocketChannel socketChannel;
    private int port;
    private String hostIp;
    private String imei;

    public NioSocketClient(int port, String hostIp, String imei) throws IOException {
        this.port = port;
        this.hostIp = hostIp;
        this.imei = imei;
        this.initialize();
    }

    private void initialize() throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        // 设置通道为非阻塞
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(hostIp, port));
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        // 启动读取线程
        new ReadThread(selector, imei);
    }

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                try {
                    new NioSocketClient(9000, "127.0.0.1", "dddd");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
