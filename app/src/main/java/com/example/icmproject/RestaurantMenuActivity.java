package com.example.icmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.icmproject.restaurantMakeOffer.RestaurantMakeOfferActivity;

public class RestaurantMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
    }

    public void makeNewOffer(View view) {
        Intent i = new Intent(getApplicationContext(), RestaurantMakeOfferActivity.class);
        startActivity(i);
    }
}