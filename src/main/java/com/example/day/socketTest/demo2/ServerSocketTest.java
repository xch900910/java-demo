package com.example.day.socketTest.demo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("server start..... listen on 9999");
            //等待客户端的连接
            Socket socket = serverSocket.accept();
            System.out.println("receive client request,start handle");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                System.out.println(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
