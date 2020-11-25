package com.example.icmproject.client;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.icmproject.Offer;
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

public class ClientSeeBookingsViewModel extends ViewModel {

    private static final String TAG = "ClientSeeBookingsVM";
    private List<Offer> offersList;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView mLocationTextView;

    public ClientSeeBookingsViewModel() {
        this.offersList = new ArrayList<>();
    }

    public List<Offer> getListOffers(){
        return offersList;
    }

    public void loadOffersForClient(ClientSeeBookingsAdapter adapter){
        offersList.clear();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser loggedIn = mAuth.getCurrentUser();

        db.collection("offers").whereArrayContains("requestedBy",loggedIn.getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            Offer of = doc.toObject(Offer.class);
                            of.setDbId(doc.getId());
                            Log.d(TAG,"Offer:"+of);
                            offersList.add(of);
                        }
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"changeAdapter");
                            if (adapter != null)adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}