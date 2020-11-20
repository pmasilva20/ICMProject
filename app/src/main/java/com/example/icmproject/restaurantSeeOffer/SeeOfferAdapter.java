package com.example.icmproject.restaurantSeeOffer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icmproject.Offer;
import com.example.icmproject.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class SeeOfferAdapter extends RecyclerView.Adapter<SeeOfferAdapter.OfferViewHolder>{

    public static final String OFFER_SELECTED = "OFFER_SELECTED";
    private static final String TAG = "seeOfferAdapter";
    private SeeOfferAdapter adapter = this;
    public List<Offer> offerList;
    private LayoutInflater inflater;


    public SeeOfferAdapter(Context context, List<Offer> listOffers) {
        inflater = LayoutInflater.from(context);
        this.offerList = listOffers;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.offer_view,
                parent, false);
        return new SeeOfferAdapter.OfferViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        Offer current = offerList.get(position);
        //Get each view and set stuff
        DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        ((TextView)holder.productView.findViewById(R.id.textViewValidade)).setText(dt.format(current.getValidade()));
        ((TextView)holder.productView.findViewById(R.id.textViewPreço)).setText(String.valueOf(current.getPrice()));
        //Replace this with Icons or smt
        ((TextView)holder.productView.findViewById(R.id.textViewEmail)).setText("Cumprido falso");
        ((TextView)holder.productView.findViewById(R.id.textViewRequesitado)).setText("Requesitado falso");

        //Set OnClick stuff
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Offer selected = offerList.get(position);
                //Go to next Acitivty with Parceable Offer
                Log.d(TAG,"Onclick "+selected.toString());
                Intent i = new Intent(v.getContext(), RestaurantOfferDetailsActivity.class);
                i.putExtra(OFFER_SELECTED,selected);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {

        public final CardView productView;
        final SeeOfferAdapter mAdapter;

        public OfferViewHolder(View itemView, SeeOfferAdapter adapter) {
            super(itemView);
            productView = itemView.findViewById(R.id.cardUserView);
            this.mAdapter = adapter;
        }
    }
}
