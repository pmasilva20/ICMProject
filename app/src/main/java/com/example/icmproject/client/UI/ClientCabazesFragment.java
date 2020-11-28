package com.example.icmproject.client.UI;

import android.os.Bundle;
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
import com.example.icmproject.client.Model.ClientCityListAdapter;
import com.example.icmproject.client.Model.ClientSeeOfferAdapter;
import com.example.icmproject.client.Model.ClientSeeOfferViewModel;

public class ClientCabazesFragment extends Fragment {

    private ClientSeeOfferViewModel vm;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewCity;
    private ClientSeeOfferAdapter adapter;
    private ClientCityListAdapter adapterCity;

    public ClientCabazesFragment() {}

    @Override
    public void onStart() {
        super.onStart();
        // Get a handle to the RecyclerView.
        recyclerView = getView().findViewById(R.id.recyclerViewSeeOffer);
        // Create an adapter and supply the data to be displayed.
        adapter = new ClientSeeOfferAdapter(getContext(), vm.getListOffers(),vm);
        // Connect the adapter with the RecyclerView.
        recyclerView.setAdapter(adapter);
        // Give the RecyclerView a default layout manager.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get a handle to the RecyclerView.
        recyclerViewCity = getView().findViewById(R.id.recyclerViewCityList);
        adapterCity = new ClientCityListAdapter(getContext(), vm.getCityList(),vm,adapter);
        // Connect the adapter with the RecyclerView.
        recyclerViewCity.setAdapter(adapterCity);
        // Give the RecyclerView a default layout manager.
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(getContext()));


        //Get Location of User,than city Address,check if in list,then loadOffers filtering by city
        vm.setAdapterCity(adapterCity);
        vm.setAdapterOffer(adapter);
        vm.getCityAddress(getActivity(),getContext());

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(ClientSeeOfferViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.client_cabazes_fragment, container, false);
    }
}