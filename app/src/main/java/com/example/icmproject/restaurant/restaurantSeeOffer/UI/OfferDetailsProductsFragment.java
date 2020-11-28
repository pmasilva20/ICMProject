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
import com.example.icmproject.restaurant.restaurantMakeOffer.Model.ProductListAdapter;
import com.example.icmproject.restaurant.restaurantSeeOffer.Model.OfferDetailsViewModel;


public class OfferDetailsProductsFragment extends Fragment {

    private static final String TAG = "offerDetailsProductsFrag";
    private RecyclerView recyclerView;
    private OfferDetailsViewModel vm;
    private ProductListAdapter adapter;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public OfferDetailsProductsFragment() {
    }


    public static OfferDetailsProductsFragment newInstance(String param1, String param2) {
        OfferDetailsProductsFragment fragment = new OfferDetailsProductsFragment();
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
        recyclerView = getView().findViewById(R.id.recyclerViewProductList);
        adapter = new ProductListAdapter(getContext(), vm.getListProductsForOffer());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(OfferDetailsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }
}