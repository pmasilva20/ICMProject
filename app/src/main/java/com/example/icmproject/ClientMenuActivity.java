package com.example.icmproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ClientMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);

        TabLayout tabLayout = findViewById(R.id.tabBar);
        TabItem tabCabazes = findViewById(R.id.tabCabazes);
        TabItem tabBookings = findViewById(R.id.tabBookings);
        ViewPager viewPager = findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new
                PagerAdapter(getSupportFragmentManager(),
                    tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Log.d("a", String.valueOf(tab.getPosition()));
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}