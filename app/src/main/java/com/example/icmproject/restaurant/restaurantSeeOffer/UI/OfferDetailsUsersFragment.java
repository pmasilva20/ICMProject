package com.example.icmproject.restaurant.restaurantSeeOffer.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.icmproject.commonDataModel.Offer;
import com.example.icmproject.R;
import com.example.icmproject.restaurant.restaurantSeeOffer.Model.OfferDetailsViewModel;
import com.example.icmproject.restaurant.restaurantSeeOffer.Model.UserListAdapter;

import java.text.SimpleDateFormat;


public class OfferDetailsUsersFragment extends Fragment {

    OnChangingToOfferDetailsProductsFragment callback;

    public void setOnChangingToOfferDetailsProductsFragment(OnChangingToOfferDetailsProductsFragment callback){
        this.callback = callback;
    }

    public interface OnChangingToOfferDetailsProductsFragment{
        public void OnChangingToOfferDetailsProductsFragment();
    }

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = "offerDetailsUsersFrag";


    private OfferDetailsViewModel vm;
    private RecyclerView recyclerView;
    private UserListAdapter adapter;

    private Offer mParam1;
    private Context context;

    public OfferDetailsUsersFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OfferDetailsUsersFragment newInstance(Offer param1) {
        OfferDetailsUsersFragment fragment = new OfferDetailsUsersFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1,param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getContext();
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG,"getting VM");
        vm = new ViewModelProvider(requireActivity()).get(OfferDetailsViewModel.class);
        //Get Views and put them on VM
        vm.setTokenInputView(getView().findViewById(R.id.editTextKey));
        vm.setTokenButtonView(getView().findViewById(R.id.buttonConfirmKey));
        // Get a handle to the RecyclerView.
        recyclerView = getView().findViewById(R.id.recyclerViewOfferDetailsUsers);
        // Create an adapter and supply the data to be displayed.
        adapter = new UserListAdapter(getContext(), vm.getUsers());
        // Connect the adapter with the RecyclerView.
        recyclerView.setAdapter(adapter);
        // Give the RecyclerView a default layout manager.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        vm.loadUsersForOffer(adapter);
        adapter.setVM(vm);
        
        vm.checkUpdateUI();

    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Configure Offer Details
        View view = inflater.inflate(R.layout.fragment_offer_details_users, container, false);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        ((TextView)view.findViewById(R.id.textViewOfferDetailsValidade)).setText(format.format(mParam1.getValidade()));
        ((TextView)view.findViewById(R.id.textViewOfferDetailsPreço)).setText(String.valueOf(mParam1.getPrice()));

        ((TextView)view.findViewById(R.id.textViewOfferDetailsCumprido)).setText("Fulfilled");
        if(mParam1.confirmedUser != null){
            ((TextView)view.findViewById(R.id.textViewOfferDetailsCumprido)).setTextColor(ContextCompat.getColor(context,R.color.green_lime));
        }
        else{
            ((TextView)view.findViewById(R.id.textViewOfferDetailsCumprido)).setTextColor(ContextCompat.getColor(context,R.color.red_wrong));
        }
        ((TextView)view.findViewById(R.id.textViewOfferDetailsRequesitado)).setText("Requested");
        if(mParam1.requestedBy != null){
            ((TextView)view.findViewById(R.id.textViewOfferDetailsRequesitado)).setTextColor(ContextCompat.getColor(context,R.color.green_lime));
        }
        else{
            ((TextView)view.findViewById(R.id.textViewOfferDetailsRequesitado)).setTextColor(ContextCompat.getColor(context,R.color.red_wrong));
        }
        return view;
    }
}