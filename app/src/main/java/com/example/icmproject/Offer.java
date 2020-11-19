package com.example.icmproject;

import java.util.Date;
import java.util.List;

public class Offer {
    public List<Product> products;
    public double price;

    public Offer(List<Product> products, double price) {
        this.products = products;
        this.price = price;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getPrice() {
        return price;
    }
    public Date getValidade(){
        Date val = products.get(0).getShelfLife();
        for(Product p :products){
            if(p.getShelfLife().before(val)){
                val = p.getShelfLife();
            }
        }
        return val;
    }
}
