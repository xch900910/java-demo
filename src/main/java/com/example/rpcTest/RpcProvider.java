package com.example.rpcTest;

import java.io.IOException;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/8 7:01
 */
public class RpcProvider {
    public static void main(String[] args) throws IOException, IllegalAccessException {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, HelloService.class, 8888);
    }
}
