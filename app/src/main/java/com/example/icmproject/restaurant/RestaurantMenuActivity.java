package com.example.icmproject.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.icmproject.R;
import com.example.icmproject.notification.NotificationManager;
import com.example.icmproject.restaurant.restaurantMakeOffer.UI.RestaurantMakeOfferActivity;
import com.example.icmproject.restaurant.restaurantSeeOffer.UI.RestaurantSeeOffersActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

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