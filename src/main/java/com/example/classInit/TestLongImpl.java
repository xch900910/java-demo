package com.example.classInit;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2021-06-21 10:31
 **/
public class TestLongImpl implements TestLong {
    @Override
    public void findId(Long id) {
        new TestInteger().getByLong(id);
    }
}
