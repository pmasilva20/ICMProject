package com.example.icmproject;

import java.util.List;

public class Offer {
    public List<Product> products;
    public double price;

    public Offer(List<Product> products, double price) {
        this.products = products;
        this.price = price;
    }
}
