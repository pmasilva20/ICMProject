package com.example.icmproject.restaurantMakeOffer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.icmproject.Product;
import com.example.icmproject.R;

public class RestaurantMakeOfferActivity extends AppCompatActivity {

    ProductListFragment frag;
    private MakeOfferViewModel vm;

    private static final String TAG = "restMakeOfferActivity";
    public static final int ADD_PRODUCT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_make_offer);
        //Setup ViewModel
        vm = new ViewModelProvider(this).get(MakeOfferViewModel.class);
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
                Product p = data.getParcelableExtra(RestaurantAddProductActivity.PRODUCT_REPLY);
                vm.addProduct(p);
                Log.d(TAG,vm.getListProducts().toString());
                frag.alertAddProduct(p);
            }
        }
    }


    public void insertOfferInDB(View view) {
        String priceText = ((TextView)findViewById(R.id.editTextPriceOffer)).getText().toString();
        if(priceText.isEmpty()) priceText = "0.0";
        double price = Double.parseDouble(priceText);
        vm.putOfferInDB(price);
    }
}