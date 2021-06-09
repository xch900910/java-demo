package com.example.socketTest.oio;

import java.io.*;
import java.net.Socket;

public class ClientSocket1_copy {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1",9999);
            BufferedWriter bufferedWriter =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
            while (true) {
                String str = bufferedReader.readLine();
                bufferedWriter.write(str);
                bufferedWriter.write("\n");
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
