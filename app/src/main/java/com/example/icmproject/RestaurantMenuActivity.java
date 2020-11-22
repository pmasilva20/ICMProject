package com.example.icmproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.icmproject.notification.NotificationManager;
import com.example.icmproject.restaurantMakeOffer.RestaurantMakeOfferActivity;
import com.example.icmproject.restaurantSeeOffer.RestaurantSeeOffersActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

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

    //TODO:Take this out
    public void testNoification(View view) {
            //Fetch from FireBase Messaging,send to FireStore
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            String token = task.getResult();
                            //TEST SEND NOTIFICATION TO MYSELF
                            new NotificationManager().execute(token);
                        }
                    });


    }
}