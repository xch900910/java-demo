package com.example.day.day05.adapter;

public class TestAdapter {
    public static void main(String[] args) {
        CellPhone cellPhone = new CellPhone("type-c");
        CommonCharger commonCharger = new CommonCharger();
//        commonCharger.charge(cellPhone);
//        TypecCharger typecCharger = new TypecCharger();
        TypecChargerAdapter typecChargerAdapter = new TypecChargerAdapter(commonCharger);
        typecChargerAdapter.charge(cellPhone);
    }
}
