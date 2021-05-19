package com.example.day.day09;

import java.io.IOException;
import java.net.Socket;

public class SocketSend {
    public static void main(String args[]) throws IOException {
        String request = "GET / HTTP/1.1\r\n" +
                "Host: localhost\r\n" +
                "Connection: keep-alive\r\n" +
                "Pragma: no-cache\r\n" +
                "Cache-Control: no-cache\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                "Sec-Fetch-Site: none\r\n" +
                "Sec-Fetch-Mode: navigate\r\n" +
                "Sec-Fetch-User: ?1\r\n" +
                "Sec-Fetch-Dest: document\r\n" +
                "Accept-Encoding: gzip, deflate, br\r\n" +
                "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8\r\n\r\n";
        String path = "/";
        String data = "name=POON";
        String host = "localhost";
        String msg = "GET " + path + "?" + data + " HTTP/1.1\r\n"
                + "Accept: */*\r\n" + "Host: " + host + "\r\n"
                + "Connection: Close\r\n";
        sendData("localhost", request);
    }

    public static void sendData(String host, String msg) throws IOException {
        Socket sock = new Socket( host, 8080 );
        sock.getOutputStream().write(msg.getBytes());
        sock.getOutputStream().flush();
        sock.close();
    }
}
