package com.example.classInit;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-21 10:29
 **/
public class TestCastException {
    public static void main(String[] args) {
        WareHouse wareHouse = new WareHouse(13);
        new TestLongImpl().findId(wareHouse.getId().longValue());
    }


}
