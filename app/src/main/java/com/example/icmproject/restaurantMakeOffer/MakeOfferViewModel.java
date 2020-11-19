package com.example.icmproject.restaurantMakeOffer;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.icmproject.Offer;
import com.example.icmproject.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MakeOfferViewModel  extends ViewModel {
    private static final String TAG = "makeOfferViewModel";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Product> productList = new ArrayList<>();

    public List<Product> getListProducts(){
        return productList;
    }

    public void setListProducts(List<Product> lst){
        productList = lst;
    }
    public void addProduct(Product p){
        productList.add(p);
    }

    public void putOfferInDB(double price) {
        List<Product> lst = productList;
        //Add Cabaz to DB
        Offer cabaz = new Offer(lst,price);
        db.collection("offers")
                .add(cabaz)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Offer added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
}
