package com.example.icmproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    public void restaurantRegisterOnClick(View view) {
        Intent i = new Intent(this,ClientMenuActivity.class);
        startActivity(i);
    }

    public void clientRegisterOnClick(View view) {
        Intent i = new Intent(this,RegisterClientActivity.class);
        startActivity(i);
    }
}