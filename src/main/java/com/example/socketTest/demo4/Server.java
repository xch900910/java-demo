package com.example.socketTest.demo4;

/**
 * @author xch900910
 * @version 1.0
 * @Desc
 * @Date 2020/12/29 20:40
 **/
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws IOException {
        new Server().start(); // 启动服务端程序
    }

    public Server() throws IOException {
        this.init(); // 初始化服务端数据
    }

    /**
     * 服务端端口
     */
    private int port = 9999;

    /**
     * 服务端的Selector用来监听Channel的事件.
     */
    private Selector selector;

    /**
     * 字符数据编码
     */
    private Charset charset = Charset.forName("UTF-8");

    /**
     * 读缓存，分配1024Byte的空间
     */
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    /**
     * 写缓存，分配1024Byte的空间
     */
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    /**
     * 存储所有客户端的Channel，转发的时候使用
     */
    private Map<String, Channel> clientSocketChannels = new HashMap<>();

    /**
     * 定义了一个线程池，服务端用来发送数据给客户端
     */
    private static ExecutorService executorService = Executors.newFixedThreadPool(1, runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.setName("server-sender");
        return thread;
    });

    /**
     * 初始化Channel.
     */
    private void init() throws IOException {
        // 声明一个服务端的ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 将服务端的ServerSocketChannel设置成非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 设置服务端的socket
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(this.port));
        // 声明一个Selector，用来监听服务端的所有Channel
        this.selector = Selector.open();
        // 在ServerSocketChannel上注册Accept事件，用来接收客户端的连接
        serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server is started, the port is " + this.port);
    }

    /**
     * 处理服务端监听到的事件
     */
    private void work(SelectionKey selectionKey) throws IOException {
        // 客户端有Socket连接请求
        if (selectionKey.isAcceptable()) {
            // 从SelectionKey中获取服务端的ServerSocketChannel，SelectionKey中包含了服务端与客户端的所有信息
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
            // 服务端打开一个新的SocketChannel用来与客户端的SocketChannel进行通信，服务端同时会随机分配一个端口
            SocketChannel socketChannel = serverSocketChannel.accept();
            // 将SocketChannel设置成非阻塞模式
            socketChannel.configureBlocking(false);
            // 将SocketChannel中的Read事件注册到Selector上
            socketChannel.register(this.selector, SelectionKey.OP_READ);
            // 存储服务端为客户端创建的SocketChannel，为后面的转发消息服务
            this.clientSocketChannels.put(this.getClientName(socketChannel), socketChannel);
            // 通过System.in IO流来创建Scanner
            Scanner scanner = new Scanner(System.in);
            // 收集服务端控制台输入的数据，通过线程池将数据广播给所有客户端SocketChannel
            this.executorService.submit(() -> {
                while (true) {
                    // 该方法会被block住，一直等到服务端控制台有数据输入完为止
                    String sendText = scanner.nextLine();
                    // 将服务端的数据广播给所有客户端
                    transferToOthers(sendText, null);
                }
            });
            // 服务端监听到有数据可以读取，主要是来源于客户端发送的数据
        } else if (selectionKey.isReadable()) {
            // 获取服务端的SocketChannel，然后与客户端进行通信
            // 需要注意的是：当前获取的SocketChannel与ServerSocketChannel是不同的，
            // 这个SocketChannel是通过调用ServerSocketChannel.accept方法创建的（存储在clientSocketChannels集合中）
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            // 清空当前的用来存储读数据的buffer
            readBuffer.clear();
            // 将数据从SocketChannel读入buffer
            int bytes = socketChannel.read(readBuffer);
            if (bytes > 0) {
                // 使得buffer中的数据可读
                readBuffer.flip();
                // 读取buffer中的数据
                String text = String.valueOf(this.charset.decode(readBuffer));
                System.out.println(this.getClientName(socketChannel) + ": " + text);
                // 将客户端发送过来的数据转发给其他客户端
                this.transferToOthers(text, socketChannel);
            }
        }
    }

    /**
     * 将数据发送给其他客户端
     */
    private void transferToOthers(String text, final SocketChannel socketChannel) {
        this.clientSocketChannels.forEach((channelName, channel) -> {
            // 获取之前存储的与服务端建立连接的客户端
            SocketChannel otherSocketChannel = (SocketChannel) channel;
            if (!otherSocketChannel.equals(socketChannel)) {
                // 清空写缓存
                this.writeBuffer.clear();
                // 将数据写入缓存
                this.writeBuffer.put(this.charset.encode(this.getClientName(socketChannel) + ": " + text));
                // 使得缓存中的数据变得可用
                this.writeBuffer.flip();
                try {
                    // 将buffer中的数据写入到其它客户端
                    otherSocketChannel.write(this.writeBuffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 通过SocketChannel生成客户端的名字，用来标识
     */
    private String getClientName(SocketChannel socketChannel) {
        if (socketChannel == null)
            return "[server]";
        Socket socket = socketChannel.socket();
        return "[" + socket.getInetAddress().toString().substring(1) + ":" + socket.getPort() + "]";
    }

    /**
     * 启动服务端程序
     */
    public void start() {
        // 无限循环来轮询所有注册的Channel
        while (true) {
            try {
                // 选择已经准备好的Channel，该方法是会block住的，直到有事件到达
                this.selector.select();
                // 获取所有监听到的事件
                Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    // 找到事件SelectionKey，里面包含了事件相关的所有数据
                    SelectionKey selectionKey = iterator.next();
                    // 如果事件是有效的
                    if (selectionKey.isValid()) {
                        // 处理事件
                        this.work(selectionKey);
                    }
                    // 删除已经处理过的事件
                    iterator.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
