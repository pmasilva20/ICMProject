package com.example.icmproject.restaurantSeeOffer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icmproject.Offer;
import com.example.icmproject.Product;
import com.example.icmproject.ProductListAdapter;
import com.example.icmproject.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class SeeOfferAdapter extends RecyclerView.Adapter<SeeOfferAdapter.OfferViewHolder>{

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
        ((TextView)holder.productView.findViewById(R.id.textViewCumprido)).setText("Cumprido falso");
        ((TextView)holder.productView.findViewById(R.id.textViewRequesitado)).setText("Requesitado falso");
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
            productView = itemView.findViewById(R.id.cardOffer);
            this.mAdapter = adapter;
        }
    }
}
