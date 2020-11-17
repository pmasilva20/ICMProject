package com.example.icmproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RestaurantMakeOfferActivity extends AppCompatActivity {

    ProductListFragment frag;
    private static final String TAG = "restMakeOfferActivity";
    public static final int ADD_PRODUCT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_make_offer);
        frag = ProductListFragment.newInstance("ex","ex2");
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerMakeOffer,
                frag).addToBackStack(null).commit();
    }

    public void insertProductOnClick(View view) {
        //Get Product from other Fragment to here,thought Activity
        //frag.addProduct(new Product("Almondegas","gramas",500.0,new Date()));
        Intent i = new Intent(this, RestaurantAddProductActivity.class);
        startActivityForResult(i,ADD_PRODUCT);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Log.e(TAG,"test");
        Log.e(TAG,String.valueOf(requestCode));
        Log.e(TAG,String.valueOf(resultCode));
        if(requestCode == ADD_PRODUCT){
            if(resultCode == RESULT_OK){
                String name = data.getStringExtra(RestaurantAddProductActivity.PName_REPLY);
                String unit = data.getStringExtra(RestaurantAddProductActivity.PUnit_REPLY);
                String quantity = data.getStringExtra(RestaurantAddProductActivity.PQuantity_REPLY);
                String shelfLife = data.getStringExtra(RestaurantAddProductActivity.PShelfLife_REPLY);
                Date actualDate;
                try {
                    actualDate = new SimpleDateFormat("dd/MM/yyyy").parse(shelfLife);
                } catch (ParseException e) {
                    e.printStackTrace();
                    actualDate = new Date();
                    Log.e(TAG,"Error,invalid shelf life date given");
                }
                Log.e(TAG,name);
                frag.addProduct(new Product(name,unit,Double.parseDouble(quantity), actualDate));
            }
        }
    }




}