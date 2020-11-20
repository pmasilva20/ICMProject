package com.example.icmproject.restaurantSeeOffer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icmproject.Offer;
import com.example.icmproject.R;

public class SeeOfferFragment extends Fragment {

    private SeeOfferViewModel vm;
    private RecyclerView recyclerView;
    private SeeOfferAdapter adapter;


    public SeeOfferFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SeeOfferFragment newInstance() {
        SeeOfferFragment fragment = new SeeOfferFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Get a handle to the RecyclerView.
        recyclerView = getView().findViewById(R.id.recyclerViewSeeOffer);
        // Create an adapter and supply the data to be displayed.
        if(vm.getListOffers() == null) Log.e("NULL","vm wrong");
        adapter = new SeeOfferAdapter(getContext(), vm.getListOffers());
        // Connect the adapter with the RecyclerView.
        recyclerView.setAdapter(adapter);
        // Give the RecyclerView a default layout manager.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Load offers of DB
        vm.loadOffersForRestaurant(adapter);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set ViewModel
        vm = new ViewModelProvider(requireActivity()).get(SeeOfferViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_see_offer, container, false);
        //Setup Offer stuff here

        return view;
    }
}