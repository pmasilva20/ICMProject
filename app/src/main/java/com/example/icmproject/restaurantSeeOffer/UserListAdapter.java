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
import com.example.icmproject.UserView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

    private static final String TAG = "userListAdapter";
    public List<UserView> userList;
    private LayoutInflater inflater;

    public UserListAdapter(Context context, List<UserView> userList) {
        inflater = LayoutInflater.from(context);
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.user_view,
                parent, false);
        return new UserListAdapter.UserViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        if (holder.productView.findViewById(R.id.textViewProductViewEmail) == null) return;
        UserView current = userList.get(position);
        //Get each view and set stuff
        ((TextView)holder.productView.findViewById(R.id.textViewProductViewEmail)).setText(current.getUsername());
        holder.productView.findViewById(R.id.buttonConfirmRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Make some call to send notification to user
                //Lock all other options
                Log.d(TAG,"Onclick,selected to lock this user: "+current.getDbId());
                //I have user ID,get from here user notification Token,generate a key for this Offer and send notification
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        public final CardView productView;
        final UserListAdapter mAdapter;

        public UserViewHolder(View itemView, UserListAdapter adapter) {
            super(itemView);
            productView = itemView.findViewById(R.id.cardUserView);
            this.mAdapter = adapter;
        }
    }
}
