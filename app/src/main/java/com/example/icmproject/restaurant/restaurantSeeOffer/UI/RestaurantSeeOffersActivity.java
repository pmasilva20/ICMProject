package com.example.icmproject.restaurant.restaurantSeeOffer.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.icmproject.R;
import com.example.icmproject.restaurant.restaurantMakeOffer.UI.RestaurantMakeOfferActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RestaurantSeeOffersActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "RestaurantSeeOffersAct";
    private SeeOfferFragment frag;
    protected BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_see_offer);
        navigationView = findViewById(R.id.bottom_navigation_restaurant_see_offer);
        navigationView.setOnNavigationItemSelectedListener(this);
        updateNavigationBarState();
        frag = SeeOfferFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.fragmentRestaurantOfferDetails,
                frag).commit();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if(itemId == R.id.page_make_offer){
                startActivity(new Intent(this, RestaurantMakeOfferActivity.class));
            }
            else if(itemId == R.id.page_see_offer){
                startActivity(new Intent(this, RestaurantSeeOffersActivity.class));
            }
            finish();
        }, 300);
        return true;
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        selectBottomNavigationBarItem(actionId);
    }

    void selectBottomNavigationBarItem(int itemId) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(true);
    }

    protected int getContentViewId() {
        return R.layout.activity_restaurant_see_offer;
    }


    protected int getNavigationMenuItemId() {
        return R.id.page_see_offer;
    }
}