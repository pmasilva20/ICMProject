package com.example.icmproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class Offer implements Parcelable {
    public List<Product> products;
    public double price;
    public String madeBy;
    private String dbId = "";

    public String getCityCreator() {
        return cityCreator;
    }

    private String cityCreator;

    public Offer(){

    }

    public String getMadeBy() {
        return madeBy;
    }

    public Offer(List<Product> products, double price, String madeBy, String cityOfRestaurant) {
        this.products = products;
        this.price = price;
        this.madeBy = madeBy;
        this.cityCreator = cityOfRestaurant;
    }

    protected Offer(Parcel in) {
        products = in.createTypedArrayList(Product.CREATOR);
        price = in.readDouble();
        madeBy = in.readString();
        dbId = in.readString();
        cityCreator = in.readString();
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(products);
        dest.writeDouble(price);
        dest.writeString(madeBy);
        dest.writeString(dbId);
        dest.writeString(cityCreator);
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }
}
