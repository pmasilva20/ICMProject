package com.example.icmproject.client.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.icmproject.commonDataModel.Offer;
import com.example.icmproject.R;
import com.example.icmproject.restaurant.restaurantSeeOffer.UI.OfferDetailsProductsFragment;
import com.example.icmproject.restaurant.restaurantSeeOffer.UI.OfferDetailsUsersFragment;
import com.example.icmproject.restaurant.restaurantSeeOffer.Model.OfferDetailsViewModel;
import com.example.icmproject.restaurant.restaurantSeeOffer.Model.SeeOfferAdapter;

public class ClientOfferDetailsActivity extends AppCompatActivity implements OfferDetailsUsersFragment.OnChangingToOfferDetailsProductsFragment{

    private static final String TAG = "restOfferDetails";
    private OfferDetailsViewModel vm;
    private OfferDetailsUsersFragment frag;
    private OfferDetailsProductsFragment fragProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_offer_details);
        vm = new ViewModelProvider(this).get(OfferDetailsViewModel.class);
        Offer offerSelected = getIntent().getParcelableExtra(SeeOfferAdapter.OFFER_SELECTED);
        Log.d(TAG,offerSelected.toString());
        vm.setOfferSelected(offerSelected);
        //Set Fragment on FrameLayout
        frag = OfferDetailsUsersFragment.newInstance(offerSelected);
        fragProducts = OfferDetailsProductsFragment.newInstance("arg1","arg2");
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.fragmentClientOfferDetails,
                fragProducts).commit();
    }
    @Override
    public void OnChangingToOfferDetailsProductsFragment() {
        //When button is clicked call this
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragmentClientOfferDetails,
                fragProducts).commit();
    }
    public void seeProductsOnClick(View view) {
        this.OnChangingToOfferDetailsProductsFragment();
    }
}