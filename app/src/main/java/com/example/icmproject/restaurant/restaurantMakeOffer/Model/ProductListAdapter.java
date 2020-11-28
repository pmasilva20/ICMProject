package com.example.icmproject.restaurant.restaurantMakeOffer.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.icmproject.commonDataModel.Product;
import com.example.icmproject.R;

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
        DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
        ((TextView)holder.productView.findViewById(R.id.textViewEmail)).setText(current.getUnit());
        ((TextView)holder.productView.findViewById(R.id.textViewRequesitado)).setText(dt.format(current.getShelfLife()));
        ((TextView)holder.productView.findViewById(R.id.textViewPreço)).setText(String.valueOf(current.getQuantity()));
        ((TextView)holder.productView.findViewById(R.id.textViewValidade)).setText(current.getName());
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
            productView = itemView.findViewById(R.id.cardUserView);
            this.mAdapter = adapter;
        }
    }
}
