package com.example.icmproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AddProductFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "addProductFragment";
    private MakeOfferViewModel vm;
    //So that when fragment calls this activity knows product has been confirmed
    public static interface OnProductConfirmedListener{
        public abstract void onProductConfirmed(Product p);
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Map<String,String> mold = new HashMap<>();
    private OnProductConfirmedListener prodListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.prodListener = (OnProductConfirmedListener)context;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnProductConfirmedListener");
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        Button b = (Button) view.findViewById(R.id.buttonAddProduct);
        b.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonAddProduct){
            //Get TextBoxes info and put them mold
            String name = ((TextView)getView().findViewById(R.id.editTextProductName)).getText().toString();
            String unit = ((TextView)getView().findViewById(R.id.editTextProductUnit)).getText().toString();
            String quantity = ((TextView)getView().findViewById(R.id.editTextProductQuantity)).getText().toString();
            String shelf_life = ((TextView)getView().findViewById(R.id.editTextProductShelfLife)).getText().toString();
            if(name.isEmpty())name = "test";
            if(unit.isEmpty())unit = "grams";
            if(quantity.isEmpty())quantity = "0";
            Date actualDate;
            if(shelf_life.isEmpty())actualDate = Calendar.getInstance().getTime();
            else{
                try {
                    actualDate = new SimpleDateFormat("dd/MM/yyyy").parse(shelf_life);
                } catch (ParseException e) {
                    e.printStackTrace();
                    actualDate = new Date();
                    Log.e(TAG,"Error,invalid shelf life date given");
                }
            }

            Product p = new Product(name,unit,Double.parseDouble(quantity),actualDate);

            //Signal activity,give product
            prodListener.onProductConfirmed(p);
        }
    }
}