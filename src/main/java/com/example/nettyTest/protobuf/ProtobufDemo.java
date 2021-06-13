package com.example.nettyTest.protobuf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/6/13 16:48
 */
public class ProtobufDemo {
    public static void main(String[] args) throws IOException {
//        System.out.println(buildMsg());
        serAndDeser1();
    }

    public static MsgProtos.Msg buildMsg() {
        MsgProtos.Msg.Builder personBuilder = MsgProtos.Msg.newBuilder();
        personBuilder.setId(1000);
        personBuilder.setContent("疯狂创客圈:高性能学习社群");
        MsgProtos.Msg message = personBuilder.build();
        return message;
    }

    public static void serAndDeser1() throws IOException {
        MsgProtos.Msg msg = buildMsg();
        byte[] bytes = msg.toByteArray();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(bytes);

        byte[] bytes1 = outputStream.toByteArray();
        MsgProtos.Msg msg1 = MsgProtos.Msg.parseFrom(bytes1);
        System.out.println("id=" + msg1.getId());
        System.out.println("content=" + msg1.getContent());
    }

}
