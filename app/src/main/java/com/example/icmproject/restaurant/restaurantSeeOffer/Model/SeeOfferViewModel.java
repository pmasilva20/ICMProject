package com.example.icmproject.restaurant.restaurantSeeOffer.Model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.icmproject.commonDataModel.Offer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SeeOfferViewModel extends ViewModel {

    private static final String TAG = "seeOfferVM";
    private List<Offer> offersList;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public SeeOfferViewModel() {
        this.offersList = new ArrayList<>();
    }

    public List<Offer> getListOffers(){
        return offersList;
    }

    public void loadOffersForRestaurant(SeeOfferAdapter adapter){
        //Reset offerList for when it returns
        offersList.clear();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser loggedIn = mAuth.getCurrentUser();
        db.collection("offers").whereEqualTo("madeBy",loggedIn.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            Log.d(TAG,"Getting document for rest:"+loggedIn.getEmail()+" ->"+doc.getData().toString());
                            Offer of = doc.toObject(Offer.class);
                            Log.w(TAG,"offer"+of);
                            of.setDbId(doc.getId());
                            offersList.add(of);
                        }
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"Adapter changing");
                            adapter.offerList = offersList;
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}