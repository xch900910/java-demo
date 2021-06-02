package com.example.day.socketTest.demo3;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-05-24 17:17
 **/
public class ReadThread implements Runnable {
    // 监听器，如果缓冲区有数据，通知程序接收
    private Selector selector;
    private Charset charset = StandardCharsets.UTF_8;
    String imei;

    public ReadThread(Selector selector, String imei) {
        this.selector = selector;
        this.imei = imei;
        new Thread(this).start();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            while (true) {
                selector.select();
                Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isConnectable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        // 如果正在连接，则完成连接
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();

                        }
                        channel.configureBlocking(false);
                        channel.write(ByteBuffer.wrap("hello".getBytes()));
                        channel.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        readMsg(key);
                    }
                    if (key.isWritable()) {
                        writeMsg(key);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        System.out.println("client接收：" + content.trim());
    }

    private void writeMsg(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        InputStream in = System.in;
        byte[] b = new byte[1024];
        int read = in.read(b);
        String string = new String(b, 0, read);
        socketChannel.write(ByteBuffer.wrap(string.getBytes()));
    }
}
