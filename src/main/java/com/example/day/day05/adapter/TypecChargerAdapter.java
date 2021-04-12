package com.example.day.day05.adapter;

import com.google.common.base.Objects;

public class TypecChargerAdapter implements Charger{
    private CommonCharger  commonCharger;
    public TypecChargerAdapter(CommonCharger commonCharger){
        this.commonCharger = commonCharger;
    }

    @Override
    public void charge(CellPhone phone) {
        commonCharger.charge(phone);
        System.out.println("开始适配");
        if (Objects.equal(phone.getChargePort(), "type-c")) {
            System.out.println("我正在为" + phone.getChargePort() + "充电");
        } else {
            System.out.println("我不能为" + phone.getChargePort() + "充电");
        }
    }
}
