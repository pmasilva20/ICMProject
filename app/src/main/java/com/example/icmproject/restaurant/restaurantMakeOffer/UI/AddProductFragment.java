package com.example.icmproject.restaurant.restaurantMakeOffer.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.icmproject.R;
import com.example.icmproject.commonDataModel.Product;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddProductFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "addProductFragment";

    public static interface OnProductConfirmedListener{
        public abstract void onProductConfirmed(Product p);
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnProductConfirmedListener prodListener;

    private String mParam1;
    private String mParam2;

    public AddProductFragment() { }

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

        Spinner spinner = (Spinner) view.findViewById(R.id.editTextProductUnit);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.product_units_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonAddProduct){

            TextInputLayout textInputLayoutName = getView().findViewById(R.id.editTextProductName);
            String name = textInputLayoutName.getEditText().getText().toString();

            String unit = ((Spinner)getView().findViewById(R.id.editTextProductUnit)).getSelectedItem().toString();


            TextInputLayout textInputLayoutQuantity = getView().findViewById(R.id.editTextProductQuantity);
            String quantity = textInputLayoutQuantity.getEditText().getText().toString();


            TextInputLayout textInputLayoutShelfLife = getView().findViewById(R.id.editTextProductShelfLife);
            String shelf_life = textInputLayoutShelfLife.getEditText().getText().toString();




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
                }
            }

            Product p = new Product(name,unit,Double.parseDouble(quantity),actualDate);

            prodListener.onProductConfirmed(p);
        }
    }
}