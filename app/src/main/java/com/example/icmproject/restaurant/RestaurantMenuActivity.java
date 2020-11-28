package com.example.icmproject.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.icmproject.R;
import com.example.icmproject.restaurant.restaurantMakeOffer.UI.RestaurantMakeOfferActivity;
import com.example.icmproject.restaurant.restaurantSeeOffer.UI.RestaurantSeeOffersActivity;

public class RestaurantMenuActivity extends AppCompatActivity {

    private static final String TAG = "restaurantMenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
    }

    public void makeNewOffer(View view) {
        Intent i = new Intent(getApplicationContext(), RestaurantMakeOfferActivity.class);
        startActivity(i);
    }

    public void seeNewOffer(View view) {
        Intent i = new Intent(getApplicationContext(), RestaurantSeeOffersActivity.class);
        startActivity(i);
    }

}