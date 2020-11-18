package com.example.icmproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    public List<Product> productList;
    private LayoutInflater inflater;

    public ProductListAdapter(Context context, List<Product> productList) {
        inflater = LayoutInflater.from(context);
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.product_view,
                parent, false);
        return new ProductViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product current = productList.get(position);
        //Get each view and set stuff
        DateFormat dt = new SimpleDateFormat("dd/mm/yyyy");
        ((TextView)holder.productView.findViewById(R.id.textViewUnit)).setText(current.getUnit());
        ((TextView)holder.productView.findViewById(R.id.textViewShelfLife)).setText(dt.format(current.getShelfLife()));
        ((TextView)holder.productView.findViewById(R.id.textViewQuantity)).setText(String.valueOf(current.getQuantity()));
        ((TextView)holder.productView.findViewById(R.id.textViewName)).setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        public final CardView productView;
        final ProductListAdapter mAdapter;

        public ProductViewHolder(View itemView, ProductListAdapter adapter) {
            super(itemView);
            productView = itemView.findViewById(R.id.cardProduct);
            this.mAdapter = adapter;
        }
    }
}
