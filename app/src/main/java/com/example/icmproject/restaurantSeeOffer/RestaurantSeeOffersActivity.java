package com.example.icmproject.restaurantSeeOffer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.icmproject.MakeOfferViewModel;
import com.example.icmproject.ProductListFragment;
import com.example.icmproject.R;

public class RestaurantSeeOffersActivity extends AppCompatActivity {

    private SeeOfferFragment frag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_see_offer);
        //Set Fragment on FrameLayout
        frag = SeeOfferFragment.newInstance("ex","ex2");
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.fragmentRestaurantSeeOffers,
                frag).addToBackStack(null).commit();

    }
}