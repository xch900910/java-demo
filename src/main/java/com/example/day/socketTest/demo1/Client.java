package com.example.day.socketTest.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) throws IOException {
        new Client().start();
    }

    public Client() throws IOException {
        this.init();
    }

    private String ip = "localhost";
    private int port = 8888;
    private Selector selector;
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private Executor executor = Executors.newFixedThreadPool(1);
    private Charset charset = Charset.forName("utf-8");

    private void init() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        this.selector = Selector.open();
        socketChannel.register(this.selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
        socketChannel.connect(new InetSocketAddress(ip, port));
    }

    private void start() throws IOException {
        while (true) {
            this.selector.select();
            Set<SelectionKey> keys = this.selector.selectedKeys();

           keys.forEach(key-> {
               try {
                   this.work(key);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           });
            keys.clear();
        }
    }

    private void work(SelectionKey key) throws IOException {
        if (key.isConnectable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            channel.configureBlocking(false);

            if (channel.isConnectionPending()) {
                channel.finishConnect();
                Scanner scanner = new Scanner(System.in);
                this.executor.execute(()->{
                    while (true) {
                        String s = scanner.nextLine();
                        try {
                            write(s,channel);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        }

        if (key.isReadable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            read(channel);
        }
    }

    private void write(String text, SocketChannel socketChannel) throws IOException {
        this.writeBuffer.clear();
        // 使得写缓冲区中的数据可读
        this.writeBuffer.put(this.charset.encode(text));
        writeBuffer.flip();
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
