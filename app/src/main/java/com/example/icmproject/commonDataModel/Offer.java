package com.example.icmproject.commonDataModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class Offer implements Parcelable {
    public List<Product> products;
    public double price;
    public String madeBy;
    public String cityCreator;
    public List<String> requestedBy;
    private String dbId = "";
    public String confirmedUser;

    @Override
    public String toString() {
        return "Offer{" +
                "products=" + products +
                ", price=" + price +
                ", madeBy='" + madeBy + '\'' +
                ", cityCreator='" + cityCreator + '\'' +
                ", requestedBy=" + requestedBy +
                ", dbId='" + dbId + '\'' +
                ", confirmedUser='" + confirmedUser + '\'' +
                ", validade=" + validade +
                '}';
    }

    public String getConfirmedUser() {
        return confirmedUser;
    }

    public void setConfirmedUser(String confirmedUser) {
        this.confirmedUser = confirmedUser;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public Date validade;

    public String getCityCreator() {
        return cityCreator;
    }

    public List<String> getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(List<String> requestedBy) {
        this.requestedBy = requestedBy;
    }

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
        validade = new Date(in.readLong());
        requestedBy = in.createStringArrayList();
        confirmedUser = in.readString();
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (Double.compare(offer.price, price) != 0) return false;
        if (!madeBy.equals(offer.madeBy)) return false;
        if (!cityCreator.equals(offer.cityCreator)) return false;
        return dbId != null ? dbId.equals(offer.dbId) : offer.dbId == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + madeBy.hashCode();
        result = 31 * result + cityCreator.hashCode();
        result = 31 * result + (dbId != null ? dbId.hashCode() : 0);
        return result;
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
        dest.writeLong(validade.getTime());
        dest.writeStringList(requestedBy);
        dest.writeString(confirmedUser);
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }
}
