package com.example.icmproject.restaurant.restaurantMakeOffer.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.icmproject.commonDataModel.Product;
import com.example.icmproject.R;

public class RestaurantAddProductActivity extends AppCompatActivity implements  AddProductFragment.OnProductConfirmedListener{

    public static final String PRODUCT_REPLY = "PRODUCT_REPLY";
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
    public void onProductConfirmed(Product p) {
        Intent reply = new Intent();
        reply.putExtra(PRODUCT_REPLY,p);
        setResult(RESULT_OK,reply);
        finish();
    }
}