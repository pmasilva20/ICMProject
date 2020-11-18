package com.example.icmproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Map;

public class RestaurantAddProductActivity extends AppCompatActivity implements  AddProductFragment.OnProductConfirmedListener{

    public static final String PName_REPLY = "PNAME";
    public static final String PUnit_REPLY = "PUNIT";
    public static final String PQuantity_REPLY = "PQUANTITY";
    public static final String PShelfLife_REPLY = "PSHELFLIFE";
    private static final String TAG = "restAddProductActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_add_product);
        Fragment frag = AddProductFragment.newInstance("ex","ex2");
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerAddProduct,
                frag).addToBackStack(null).commit();
    }


    @Override
    public void onProductConfirmed(Map<String,String> product_input) {
        //Product has been confirmed
        Intent reply = new Intent();
        reply.putExtra(PName_REPLY,product_input.get("name"));
        reply.putExtra(PUnit_REPLY,product_input.get("unit"));
        reply.putExtra(PQuantity_REPLY,product_input.get("quantity"));
        reply.putExtra(PShelfLife_REPLY,product_input.get("shelf_life"));
        Log.e(TAG,"test2");
        setResult(RESULT_OK,reply);
        finish();
    }
}