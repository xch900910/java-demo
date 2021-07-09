package com.example.rpcTest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/8 7:00
 * rpc需要的技术
 * 1.动态代理（cglib,jdk）
 * 2.注册中心(zk,consul)
 * 3.协议容器（netty）
 * 4.序列化(jdk,protobuf)
 */
public class RpcFramework {
    private static Executor executor = new ThreadPoolExecutor(10, 20,
            60, TimeUnit.HOURS, new LinkedBlockingDeque<>());

    public static void export(Object service, Class interfaceClazz, int port) throws IllegalAccessException, IOException {
        if (service == null) {
            throw new IllegalAccessException("service instance == null");
        }
        if (port < 0 || port > 65535) {
            throw new IllegalAccessException("Invalid port " + port);
        }
        System.out.println("Export service " + service.getClass().getName() + " on port " + port);
        ServerSocket serverSocket = new ServerSocket(port);
        for (; ; ) {
            Socket socket = serverSocket.accept();
            executor.execute(() -> {
                try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());) {

                    String interfaceName = input.readUTF();
                    String methodName = input.readUTF();
                    Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                    Object[] arguments = (Object[]) input.readObject();
                    if (!interfaceName.equals(interfaceClazz.getName())) {
                        throw new IllegalAccessException("Interface wrong, export:" + interfaceClazz
                                + " refer:" + interfaceName);
                    }
                    Method method = service.getClass().getMethod(methodName, parameterTypes);
                    Object result = method.invoke(service, arguments);

                    objectOutputStream.writeObject(result);
                } catch (Exception e) {
                    e.printStackTrace();

                }

            });
        }
    }

    public static <T> T refer(Class<T> interfaceClass, String host, int port) throws IllegalAccessException {
        if (interfaceClass == null) {
            throw new IllegalAccessException("Interface class == null");
        }
        if (!interfaceClass.isInterface()) {
            throw new IllegalAccessException(interfaceClass.getName() + " must be interface");
        }
        if (host == null || host.length() == 0) {
            throw new IllegalAccessException("host == null");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalAccessException("Invalid port " + port);
        }
        System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);

        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket(host, port);
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());) {
                    objectOutputStream.writeUTF(interfaceClass.getName());
                    objectOutputStream.writeUTF(method.getName());
                    objectOutputStream.writeObject(method.getParameterTypes());
                    objectOutputStream.writeObject(args);

                    Object result = objectInputStream.readObject();
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        });

    }
}
