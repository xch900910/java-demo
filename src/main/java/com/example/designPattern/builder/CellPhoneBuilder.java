package com.example.designPattern.builder;

/**
 * @author xch900910
 * @version 1.0
 * @date 2021/7/4 15:35
 */
public class CellPhoneBuilder {
    private long id;
    private String cup;
    private String ram;
    private int size;
    private Double weight;

    private CellPhoneBuilder() {

    }

    public static CellPhoneBuilder builder() {
        return new CellPhoneBuilder();
    }

    public CellPhoneBuilder id(long id) {
        this.id = id;
        return this;
    }

    public CellPhoneBuilder cup(String cup) {
        this.cup = cup;
        return this;
    }

    public CellPhoneBuilder ram(String ram) {
        this.ram = ram;
        return this;
    }

    public CellPhoneBuilder size(int size) {
        this.size = size;
        return this;
    }

    public CellPhoneBuilder weight(double weight) {
        this.weight = weight;
        return this;
    }

    public CellPhone build() {
        CellPhone cellPhone = new CellPhone();
        cellPhone.setId(id);
        cellPhone.setSize(size);
        cellPhone.setCup(cup);
        cellPhone.setRam(ram);
        cellPhone.setWeight(weight);
        return cellPhone;
    }
}
