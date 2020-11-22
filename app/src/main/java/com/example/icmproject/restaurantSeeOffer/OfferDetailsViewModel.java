package com.example.icmproject.restaurantSeeOffer;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icmproject.Offer;
import com.example.icmproject.Product;
import com.example.icmproject.UserView;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OfferDetailsViewModel extends ViewModel {

    private static final String TAG = "offerDetailsViewModel";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Offer offerSelected;
    private List<Product> productsInOfferSelected;
    private List<String> idsUsersWhoRequested = new ArrayList<>();
    private List<UserView> usersWhoRequested = new ArrayList<>();


    public Offer getOfferSelected() {
        return offerSelected;
    }

    public void setOfferSelected(Offer offerSelected) {
        this.offerSelected = offerSelected;
    }

    public OfferDetailsViewModel(){ }

    //TODO:Get users that requested this,maybe list of users Id's in offers
    //Kinda done
    public void loadUsersForOffer(UserListAdapter adapter){
        idsUsersWhoRequested = new ArrayList<>();
        Log.d(TAG,"LoadFromDB");
        db.collection("offers").document(offerSelected.getDbId()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            idsUsersWhoRequested = (List<String>) task.getResult().get("requestedBy");
                            if (idsUsersWhoRequested != null){
                                Log.d(TAG,idsUsersWhoRequested.toString());
                                loadUserNames(adapter);
                            }
                        }
                    }
                });
    }

    private void loadUserNames(UserListAdapter adapter) {
        usersWhoRequested.clear();
        for(String idUser : idsUsersWhoRequested){
            db.collection("users").document(idUser).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                String mail = (String) task.getResult().get("email");
                                UserView user = new UserView(mail,idUser);
                                Log.e(TAG,usersWhoRequested.toString());
                                usersWhoRequested.add(user);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

    }

    public List<UserView> getUsers(){
        return usersWhoRequested;
    }

    public List<Product> getListProductsForOffer() {
        return offerSelected.getProducts();
    }
}
