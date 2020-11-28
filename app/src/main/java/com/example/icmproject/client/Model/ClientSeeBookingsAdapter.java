package com.example.icmproject.client.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icmproject.commonDataModel.Offer;
import com.example.icmproject.R;
import com.example.icmproject.client.UI.ClientOfferDetailsActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ClientSeeBookingsAdapter extends RecyclerView.Adapter<ClientSeeBookingsAdapter.ClientBookingsViewHolder>{


    public static final String OFFER_SELECTED = "OFFER_SELECTED";
    private static final String TAG = "ClientSeeBookingAdapter";
    private ClientSeeBookingsAdapter adapter = this;
    public List<Offer> offerList;
    private LayoutInflater inflater;
    private Context context;


    public ClientSeeBookingsAdapter(Context context, List<Offer> listOffers) {
        inflater = LayoutInflater.from(context);
        this.offerList = listOffers;
        this.context = context;
    }

    @NonNull
    @Override
    public ClientBookingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.offer_view,
                parent, false);
        return new ClientSeeBookingsAdapter.ClientBookingsViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientBookingsViewHolder holder, int position) {
        Offer current = offerList.get(position);
        //Get each view and set stuff
        DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        ((TextView)holder.productView.findViewById(R.id.textViewValidade)).setText(dt.format(current.getValidade()));
        ((TextView)holder.productView.findViewById(R.id.textViewPreço)).setText(String.valueOf(current.getPrice()));
        //Replace this with Icons or smt
        ((TextView)holder.productView.findViewById(R.id.textViewEmail)).setText("Fulfilled");
        if(current.confirmedUser != null){
            ((TextView)holder.productView.findViewById(R.id.textViewEmail)).setTextColor(ContextCompat.getColor(context,R.color.green_lime));
        }
        else{
            ((TextView)holder.productView.findViewById(R.id.textViewEmail)).setTextColor(ContextCompat.getColor(context,R.color.red_wrong));
        }
        ((TextView)holder.productView.findViewById(R.id.textViewRequesitado)).setText("Requested");
        if(current.requestedBy != null){
            ((TextView)holder.productView.findViewById(R.id.textViewRequesitado)).setTextColor(ContextCompat.getColor(context,R.color.green_lime));
        }
        else{
            ((TextView)holder.productView.findViewById(R.id.textViewRequesitado)).setTextColor(ContextCompat.getColor(context,R.color.red_wrong));
        }
        //Set OnClick stuff
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Offer selected = offerList.get(position);
                //Go to next Activity with Parcelable Offer
                Intent i = new Intent(v.getContext(), ClientOfferDetailsActivity.class);
                i.putExtra(OFFER_SELECTED,selected);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class ClientBookingsViewHolder extends RecyclerView.ViewHolder {

        public final CardView productView;
        final ClientSeeBookingsAdapter mAdapter;

        public ClientBookingsViewHolder(View itemView, ClientSeeBookingsAdapter adapter) {
            super(itemView);
            productView = itemView.findViewById(R.id.cardUserView);
            this.mAdapter = adapter;
        }
    }

}
