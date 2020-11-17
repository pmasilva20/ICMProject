package com.example.icmproject;

import java.util.Date;

public class Product {
    private String name;
    private String unit;
    private double quantity;
    private Date shelfLife;

    public Product(String name, String unit, double quantity, Date shelfLife) {
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.shelfLife = shelfLife;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Date getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(Date shelfLife) {
        this.shelfLife = shelfLife;
    }
}
