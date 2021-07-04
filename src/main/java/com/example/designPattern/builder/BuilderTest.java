package com.example.designPattern.builder;

import com.example.nettyTest.jsonEcho.RandomUtil;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/4 15:57
 */
public class BuilderTest {
    public static void main(String[] args) {
        CellPhoneBuilder builder = CellPhoneBuilder.builder();
        CellPhone cellPhone = builder.size(100).id(RandomUtil.randInMod(1000))
                .ram("6G").weight(159.78).build();
        System.out.println(cellPhone.toString());
    }
}
