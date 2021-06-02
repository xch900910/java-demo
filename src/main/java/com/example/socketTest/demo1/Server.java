package com.example.socketTest.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        new Server().start();
    }

    private Selector selector;
    private int port = 8888;

    public Server() throws IOException {
        this.init();
    }

    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private Executor executor = Executors.newFixedThreadPool(1);
    private Charset charset = Charset.forName("utf-8");

    private void init() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.bind(new InetSocketAddress(this.port));
        this.selector = Selector.open();
        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        System.out.println("server listen on " + this.port);
    }

    private void start() throws IOException {
        while (true) {
            int events = this.selector.select();
            if (events > 0) {
                Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isValid()) {
                        this.work(key);
                    }
                }
                iterator.remove();
            }
        }
    }

    private void work(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            SocketChannel accept = channel.accept();
            accept.configureBlocking(false);
            Scanner scanner = new Scanner(System.in);
            executor.execute(() -> {
                while (true) {
                    // 该方法会被block住，一直等到服务端控制台有数据输入完为止
                    String sendText = scanner.nextLine();

                    try {
                        write(sendText, accept);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            accept.register(this.selector, SelectionKey.OP_READ);
        }
        if (key.isReadable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            read(channel);
        }
    }

    private void write(String text, SocketChannel socketChannel) throws IOException {
        this.writeBuffer.clear();
        this.writeBuffer.put(this.charset.encode(text));
        this.writeBuffer.flip();
        socketChannel.write(this.writeBuffer);

    }

    private void read(SocketChannel socketChannel) throws IOException {
        this.readBuffer.clear();
        int read = socketChannel.read(this.readBuffer);
        if (read>0) {
            readBuffer.flip();
            CharBuffer decode = this.charset.decode(this.readBuffer);
            String s = String.valueOf(decode);
            System.out.println(s);
        }
    }

}
