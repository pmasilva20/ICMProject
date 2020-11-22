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

public class ClientBookingsFragment extends Fragment {

    private ClientSeeBookingsViewModel vm;
    private RecyclerView recyclerView;
    private ClientSeeBookingsAdapter adapter;

    public ClientBookingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        // Get a handle to the RecyclerView.
        recyclerView = getView().findViewById(R.id.recyclerViewSeeOfferBooking);
        // Create an adapter and supply the data to be displayed.
        if(vm.getListOffers() == null) Log.e("NULL","vm wrong");
        adapter = new ClientSeeBookingsAdapter(getContext(), vm.getListOffers());
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
        vm = new ViewModelProvider(requireActivity()).get(ClientSeeBookingsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.client_bookings_fragment, container, false);
    }
}
