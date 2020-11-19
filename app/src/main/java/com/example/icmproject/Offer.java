package com.example.icmproject;

import java.util.Date;
import java.util.List;

public class Offer {
    public List<Product> products;
    public double price;
    public String madeBy;

    public Offer(){

    }
    public Offer(List<Product> products, double price,String madeBy) {
        this.products = products;
        this.price = price;
        this.madeBy = madeBy;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getPrice() {
        return price;
    }
    public Date getValidade(){
        //TODO:Corrigir este bug,por vezes products é null
        if(products == null)return new Date();
        Date val = products.get(0).getShelfLife();
        for(Product p :products){
            if(p.getShelfLife().before(val)){
                val = p.getShelfLife();
            }
        }
        return val;
    }
}
