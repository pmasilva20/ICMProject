package com.example.icmproject.restaurantSeeOffer;

import androidx.lifecycle.ViewModel;

import com.example.icmproject.Offer;
import com.example.icmproject.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeeOfferViewModel extends ViewModel {

    private List<Offer> offersList;

    public SeeOfferViewModel() {
        this.offersList = new ArrayList<>();
    }

    public List<Offer> getListOffers(){
        return offersList;
    }

    public void loadOffersForRestaurant(){
        List<Product> ls = new ArrayList<Product>();
        ls.add(new Product("d","gramas",5.0,new Date()));
        offersList.add(new Offer(ls,30.0));
    }
}
