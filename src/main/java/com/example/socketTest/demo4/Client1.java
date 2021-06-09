package com.example.socketTest.demo4;

/**
 * @author xch900910
 * @version 1.0
 * @Desc
 * @Date 2020/12/29 20:41
 **/
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Client1 {

    public static void main(String[] args) throws IOException {
        new Client1().start(); // 客户端程序执行入口
    }

    /**
     * 注册监听的服务的端口，并初始化
     */
    public Client1() throws IOException {
        this.serverSocketAddress = new InetSocketAddress("127.0.0.1", 9999);
        this.init();
    }

    /**
     * 服务的Socket地址
     */
    private SocketAddress serverSocketAddress;

    /**
     * 客户端Selector
     */
    private Selector selector;

    /**
     * 字符编码
     */
    private Charset charset = Charset.forName("UTF-8");

    /**
     * 读缓冲区
     */
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);

    /**
     * 写缓冲区
     */
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    /**
     * 线程池执行客户端发送数据
     */
    private static ExecutorService executorService = Executors.newFixedThreadPool(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            thread.setName("client-sender");
            return thread;
        }
    });

    /**
     * 初始化客户端信息
     */
    private void init() throws IOException {
        // 声明一个客户端SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        // 设置成非阻塞模式
        socketChannel.configureBlocking(false);
        // 声明一个Selector
        this.selector = Selector.open();
        // 将客户端的SocketChannel的连接事件注册到selector上
        socketChannel.register(this.selector, SelectionKey.OP_CONNECT);
        // 连接服务端
        socketChannel.connect(this.serverSocketAddress);
    }

    /**
     * 处理客户端数据
     */
    private void work(SelectionKey selectionKey) {
        try {
            //  与服务端建立连接
            if (selectionKey.isConnectable()) {
                // 从SelectionKey中获取客户端的ServerSocketChannel
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                // 判断连接是否完成
                if (socketChannel.isConnectionPending()) {
                    // 完成连接
                    socketChannel.finishConnect();
                    System.out.println("The connection is successful！");
                    // 通过System.in IO流来创建Scanner
                    Scanner scanner = new Scanner(System.in);
                    // 使用线程池来完成对客户端的控制台数据输入的监听
                    executorService.submit(() -> {
                        while (true) {
                            try {
                                // 清空写缓冲区
                                writeBuffer.clear();
                                // 该方法会被block住，一直等到客户端控制台有数据输入完为止
                                String sendText = scanner.nextLine();
                                // 将数据写入写缓冲区
                                writeBuffer.put(charset.encode(sendText));
                                // 使得写缓冲区中的数据可读
                                writeBuffer.flip();
                                // 将数据通过SocketChannel发送到服务端
                                socketChannel.write(writeBuffer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                // 注册可读事件，应该当前的SocketChannel与服务端建立连接以后，不需要再监听创建连接的事件
                // 为了复用SocketChannel，将SocketChannel的Read事件注册到Selector
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            // 可读事件，有从服务器端发送过来的信息，读取输出到控制台上
            else if (selectionKey.isReadable()) {
                // 获取与服务端通信的客户端SocketChannel
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                // 清空读缓冲区
                this.readBuffer.clear();
                // 将数据读取到读缓冲区，并将数据输出到客户端控制台
                int count = socketChannel.read(this.readBuffer);
                if (count > 0) {
                    String text = new String(this.readBuffer.array(), 0, count);
                    System.out.println(text);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动客户端程序
     */
    public void start() throws IOException {
        // 无限循环，轮询所有监听的SocketChannel
        while (true) {
            // 选择已经准备好的Channel，该方法是会block住的，直到有事件到达
            int events = this.selector.select();
            if (events > 0) {
                // 找到事件SelectionKey，里面包含了事件相关的所有数据
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                // 处理事件
                selectionKeys.forEach(selectionKey -> this.work(selectionKey));
                // 清空已处理的事件
                selectionKeys.clear();
            }
        }
    }
}
