package com.example.icmproject.client.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icmproject.R;

import java.util.List;

public class ClientCityListAdapter extends RecyclerView.Adapter<ClientCityListAdapter.CityItemViewHolder>{

    private static final String TAG = "clientCityListAdapter";
    private final ClientSeeOfferAdapter adapter;
    private LayoutInflater inflater;
    public List<String> citiesList;
    public String citySelected = "";
    public ClientSeeOfferViewModel vm;

    public ClientCityListAdapter(Context context, List<String> cityLst, ClientSeeOfferViewModel vm, ClientSeeOfferAdapter adapter) {
        inflater = LayoutInflater.from(context);
        this.citiesList = cityLst;
        this.vm = vm;
        this.adapter = adapter;
    }

    @NonNull
    @Override
    public CityItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.city_view,
                parent, false);
        return new ClientCityListAdapter.CityItemViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CityItemViewHolder holder, int position) {
        //Get each view and set stuff
        String city = citiesList.get(position);
        ((Button)holder.itemView.findViewById(R.id.textViewCityItem)).setText(city);
        //Set OnClick stuff
        ((Button)holder.itemView.findViewById(R.id.textViewCityItem)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Clicked city:"+((Button)v).getText());
                citySelected = (String) ((Button)v).getText();
                vm.loadOffersForClient(adapter);
            }
        });
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public class CityItemViewHolder extends RecyclerView.ViewHolder {

        public final TextView cityView;
        final ClientCityListAdapter mAdapter;

        public CityItemViewHolder(View itemView, ClientCityListAdapter adapter) {
            super(itemView);
            cityView = itemView.findViewById(R.id.textViewCityItem);
            this.mAdapter = adapter;
        }
    }
}
