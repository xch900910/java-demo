package com.example.designPattern.adapter;

import lombok.Data;

@Data
public class CellPhone {
    private String name;
    private String chargePort;

    public CellPhone(String chargePort) {
        this.chargePort = chargePort;
    }

}
