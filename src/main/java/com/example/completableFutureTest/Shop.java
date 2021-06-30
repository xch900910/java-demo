package com.example.completableFutureTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-25 15:51
 **/
@Data
@AllArgsConstructor
public class Shop {
    /**
     * 商店名称
     */
    private String name;

    /**
     * 获取商品价格(同步)
     */
    public double getPrice(String product) {
        // 会查询商店的数据库，但也有可能执行一些其他耗时的任务，比如联系其
        // 他外部服务（比如，商店的供应商，或者跟制造商相关的推广折扣）
        // 模拟这些工作的耗时
        try {
            TimeUnit.MILLISECONDS.sleep(500 + RandomUtils.nextInt(10, 1000));
        } catch (InterruptedException ignored) {
        }
        // 模拟价格
        return RandomUtils.nextDouble(1, 10) * product.charAt(0) + product.charAt(1);
    }

    /**
     * 获取商品价格(异步)
     */
    public Future<Double> getPriceAsync(String product) {
        // 创建CompletableFuture对象，它会包含计算的结果
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500 + RandomUtils.nextInt(10, 1000));
                // 模拟价格
                double price = RandomUtils.nextDouble(1, 10) * product.charAt(0) + product.charAt(1);
                // 需长时间计算的任务结束并得出结果时，设置Future的返回值
                futurePrice.complete(price);
            } catch (Exception ex) {
                // 抛出导致失败的异常，完成这次Future操作
                futurePrice.completeExceptionally(ex);
            }
        }).start();
        // 无需等待还没结束的计算，直接返回Future对象
        return futurePrice;
    }
}
