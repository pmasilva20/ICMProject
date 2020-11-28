package com.example.icmproject.restaurant.restaurantSeeOffer.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icmproject.R;
import com.example.icmproject.restaurant.restaurantSeeOffer.Model.SeeOfferAdapter;
import com.example.icmproject.restaurant.restaurantSeeOffer.Model.SeeOfferViewModel;

public class SeeOfferFragment extends Fragment {

    private SeeOfferViewModel vm;
    private RecyclerView recyclerView;
    private SeeOfferAdapter adapter;


    public SeeOfferFragment() {
    }


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
        recyclerView = getView().findViewById(R.id.recyclerViewSeeOffer);
        adapter = new SeeOfferAdapter(getContext(), vm.getListOffers());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        vm.loadOffersForRestaurant(adapter);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(SeeOfferViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_see_offer, container, false);

        return view;
    }
}