package com.example.day.day05.adapter;

import lombok.Data;

@Data
public  class CellPhone {
    private String name;
    private String chargePort;
    public CellPhone(String chargePort){
        this.chargePort = chargePort;
    }

}
