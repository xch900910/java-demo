package com.example.day.socketTest.demo2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketThreadTest {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket  =new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(()->{
                    BufferedReader bufferedReader =null;
                    try {
                        bufferedReader =new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                        String str;
                        while ((str = bufferedReader.readLine())!=null){
                            System.out.println("客户端说："+str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
