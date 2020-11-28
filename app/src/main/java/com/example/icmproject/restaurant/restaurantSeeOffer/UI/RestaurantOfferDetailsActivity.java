package com.example.icmproject.restaurant.restaurantSeeOffer.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.icmproject.commonDataModel.Offer;
import com.example.icmproject.R;
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
        Log.d(TAG,offerSelected.toString());
        vm.setOfferSelected(offerSelected);
        //Set Fragment on FrameLayout
        frag = OfferDetailsUsersFragment.newInstance(offerSelected);
        fragProducts = OfferDetailsProductsFragment.newInstance("arg1","arg2");
        // Get the FragmentManager and start a transaction.
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
        //When button is clicked call this
        // Get the FragmentManager and start a transaction.
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
        //Tell VM to make querie,if wrong Toast,if yes,Offer iis done for
        vm.checKeyAuth(this);
    }
}