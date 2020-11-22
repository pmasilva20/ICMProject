package com.example.icmproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.icmproject.authentification.LoginActivity;
import com.example.icmproject.authentification.MainMenuActivity;
import com.example.icmproject.authentification.RegisterClientActivity;
import com.example.icmproject.authentification.RegisterRestaurantActivity;
import com.example.icmproject.client.ClientMenuActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this, ClientMenuActivity.class);
        startActivity(i);
    }
}