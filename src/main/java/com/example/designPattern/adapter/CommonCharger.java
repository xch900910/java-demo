package com.example.designPattern.adapter;

import com.google.common.base.Objects;

public class CommonCharger implements Charger {

    @Override
    public void charge(CellPhone phone) {
        if (Objects.equal(phone.getChargePort(), "common")) {
            System.out.println("我正在为" + phone.getChargePort() + "充电");
        } else {
            System.out.println("我不能为" + phone.getChargePort() + "充电");
        }
    }
}
