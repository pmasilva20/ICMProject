package com.example.icmproject.commonDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Product implements Parcelable {
    private String name;
    private String unit;
    private double quantity;
    private Date shelfLife;

    public Product(){}

    public Product(String name, String unit, double quantity, Date shelfLife) {
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.shelfLife = shelfLife;
    }

    protected Product(Parcel in) {
        name = in.readString();
        unit = in.readString();
        quantity = in.readDouble();
        shelfLife = new Date(in.readLong());
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(unit);
        dest.writeDouble(quantity);
        dest.writeLong(shelfLife.getTime());
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", quantity=" + quantity +
                ", shelfLife=" + shelfLife +
                '}';
    }
}
