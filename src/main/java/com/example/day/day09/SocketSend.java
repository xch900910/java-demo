package com.example.day.day09;

import java.io.IOException;
import java.net.Socket;

public class SocketSend {
    public static void main(String args[]) throws IOException {
        sendData("localhost", "GET / HTTP/1.1\r\n" +
                "Host: localhost:8080\n" +
                "Connection: keep-alive\n" +
                "Pragma: no-cache\n" +
                "Cache-Control: no-cache\n" +
                "sec-ch-ua: \" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"\n" +
                "sec-ch-ua-mobile: ?0\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-User: ?1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8");
    }

    public static void sendData(String host, String msg) throws IOException {
        Socket sock = new Socket( host, 8080 );
        sock.getOutputStream().write(msg.getBytes());
        sock.getOutputStream().flush();
        sock.close();
    }
}
