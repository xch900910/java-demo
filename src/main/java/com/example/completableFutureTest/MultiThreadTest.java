package com.example.completableFutureTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-25 15:53
 **/

public class MultiThreadTest {
    /**
     * 初始化4个商店
     */
    static List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"));

    public static List<Double> findPrices(String product, List<Shop> shops) {
        return shops.stream().map(shop -> shop.getPrice(product)).collect(toList());
    }

    //    public static void main(String[] args) {
//        long start = System.nanoTime();
//        List<Double> prices = findPrices("肥皂", shops);
//        //这里不可以做其他事情
//        doSomethingElse();
//        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
//        System.out.println("同步状态下查询价格耗时" + invocationTime + " ms," + "价格列表:" + prices);
//    }
    public static void main(String[] args) {
        long start = System.nanoTime();
        List<Future<Double>> pricesFuture = findPricesAsync("肥皂", shops);
        // 这里可以做其他事情
        doSomethingElse();
        List<Double> prices = pricesFuture.stream()
                .map(doubleFuture -> {
                    try {
                        return doubleFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(toList());
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("异步状态下查询价格耗时" + invocationTime + " ms," + "价格列表:" + prices);
    }

    public static List<Future<Double>> findPricesAsync(String product, List<Shop> shops) {
        return shops.stream().map(shop -> shop.getPriceAsync(product)).collect(toList());
    }

    private static void doSomethingElse() {
        // 其他任务...
        System.out.println("doSomethingElse");
    }
}
