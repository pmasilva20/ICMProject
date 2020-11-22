package com.example.icmproject.client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.icmproject.Offer;
import com.example.icmproject.R;
import com.example.icmproject.restaurantSeeOffer.OfferDetailsUsersFragment;
import com.example.icmproject.restaurantSeeOffer.OfferDetailsViewModel;
import com.example.icmproject.restaurantSeeOffer.SeeOfferAdapter;

public class ClientOfferDetailsActivity extends AppCompatActivity {

    private static final String TAG = "restOfferDetails";
    private OfferDetailsViewModel vm;
    private OfferDetailsUsersFragment frag;

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
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.fragmentClientOfferDetails,
                frag).addToBackStack(null).commit();
    }
}