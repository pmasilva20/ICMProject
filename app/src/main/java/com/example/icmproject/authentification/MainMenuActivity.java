package com.example.icmproject.authentification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.icmproject.R;

public class MainMenuActivity extends AppCompatActivity {


    private static final String TAG = "mainMenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void goToRegister(View view) {
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    public void goToLogin(View view) {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }


}