package com.example.icmproject.restaurant.restaurantSeeOffer.UI;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.icmproject.R;
import com.example.icmproject.commonDataModel.Offer;
import com.example.icmproject.restaurant.restaurantSeeOffer.Model.OfferDetailsViewModel;
import com.example.icmproject.restaurant.restaurantSeeOffer.Model.SeeOfferAdapter;

public class RestaurantOfferDetailsActivity extends AppCompatActivity implements OfferDetailsUsersFragment.OnChangingToOfferDetailsProductsFragment {

    private static final String TAG = "restOfferDetails";
    private OfferDetailsViewModel vm;
    private OfferDetailsUsersFragment frag;
    private OfferDetailsProductsFragment fragProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_offer_details);
        vm = new ViewModelProvider(this).get(OfferDetailsViewModel.class);
        Offer offerSelected = getIntent().getParcelableExtra(SeeOfferAdapter.OFFER_SELECTED);
        vm.setOfferSelected(offerSelected);
        frag = OfferDetailsUsersFragment.newInstance(offerSelected);
        fragProducts = OfferDetailsProductsFragment.newInstance("arg1","arg2");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.fragmentRestaurantOfferDetails,
                frag).commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment){
        if(fragment instanceof  OfferDetailsUsersFragment){
            OfferDetailsUsersFragment frag = (OfferDetailsUsersFragment) fragment;
            frag.setOnChangingToOfferDetailsProductsFragment(this);
        }
    }

    @Override
    public void OnChangingToOfferDetailsProductsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragmentRestaurantOfferDetails,
                fragProducts).addToBackStack(null).commit();

    }

    public void seeProductsOnClick(View view) {
        this.OnChangingToOfferDetailsProductsFragment();
    }

    public void confirmKeyOnClick(View view) {
        vm.checKeyAuth(this);
    }
}