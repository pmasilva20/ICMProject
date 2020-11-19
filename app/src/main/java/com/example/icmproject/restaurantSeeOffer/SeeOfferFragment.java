package com.example.icmproject.restaurantSeeOffer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icmproject.R;

public class SeeOfferFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private SeeOfferViewModel vm;
    private RecyclerView recyclerView;
    private SeeOfferAdapter adapter;


    public SeeOfferFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SeeOfferFragment newInstance(String param1, String param2) {
        SeeOfferFragment fragment = new SeeOfferFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //Load offers of DB
        vm.loadOffersForRestaurant();
        // Get a handle to the RecyclerView.
        recyclerView = getView().findViewById(R.id.recyclerViewSeeOffer);
        // Create an adapter and supply the data to be displayed.
        adapter = new SeeOfferAdapter(getContext(), vm.getListOffers());
        // Connect the adapter with the RecyclerView.
        recyclerView.setAdapter(adapter);
        // Give the RecyclerView a default layout manager.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        return inflater.inflate(R.layout.fragment_see_offer, container, false);
    }
}