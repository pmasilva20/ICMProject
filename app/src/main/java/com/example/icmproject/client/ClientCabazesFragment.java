package com.example.icmproject.client;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icmproject.R;
import com.example.icmproject.client.ClientSeeOfferAdapter;
import com.example.icmproject.client.ClientSeeOfferViewModel;

public class ClientCabazesFragment extends Fragment {

    private ClientSeeOfferViewModel vm;
    private RecyclerView recyclerView;
    private ClientSeeOfferAdapter adapter;

    public ClientCabazesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        // Get a handle to the RecyclerView.
        recyclerView = getView().findViewById(R.id.recyclerViewSeeOffer);
        // Create an adapter and supply the data to be displayed.
        if(vm.getListOffers() == null) Log.e("NULL","vm wrong");
        adapter = new ClientSeeOfferAdapter(getContext(), vm.getListOffers());
        // Connect the adapter with the RecyclerView.
        recyclerView.setAdapter(adapter);
        // Give the RecyclerView a default layout manager.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Load offers of DB
        vm.loadOffersForClient(adapter);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set ViewModel
        vm = new ViewModelProvider(requireActivity()).get(ClientSeeOfferViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.client_cabazes_fragment, container, false);
    }
}