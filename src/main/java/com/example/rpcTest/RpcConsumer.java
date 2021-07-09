package com.example.rpcTest;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/8 7:00
 */
public class RpcConsumer {
    public static void main(String[] args) throws IllegalAccessException {
        HelloService helloService = RpcFramework.refer(HelloService.class, "127.0.0.1", 8888);
        String result = helloService.hello("sam");
        System.out.println(result);
    }
}
