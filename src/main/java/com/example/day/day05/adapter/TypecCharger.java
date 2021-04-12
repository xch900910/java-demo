package com.example.day.day05.adapter;

import com.google.common.base.Objects;

public class TypecCharger implements Charger {
    private TypecCharger(){
        //不可用
    }
    @Override
    public void charge(CellPhone phone) {
        if (Objects.equal(phone.getChargePort(), "type-c")) {
            System.out.println("我正在为" + phone.getChargePort() + "充电");
        } else {
            System.out.println("我不能为" + phone.getChargePort() + "充电");
        }
    }
}
