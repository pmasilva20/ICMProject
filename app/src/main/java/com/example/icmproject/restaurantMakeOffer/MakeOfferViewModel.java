package com.example.icmproject.restaurantMakeOffer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.icmproject.Offer;
import com.example.icmproject.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MakeOfferViewModel  extends ViewModel {
    private static final String TAG = "makeOfferViewModel";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth;
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

    public void putOfferInDB(double price, Context applicationContext) {
        auth = FirebaseAuth.getInstance();
        List<Product> lst = productList;
        db.collection("users")
                .document(auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            String city = (String)task.getResult().get("city");
                            //Add Cabaz to DB
                            Offer cabaz = new Offer(lst,price,auth.getCurrentUser().getUid(),city);
                            db.collection("offers")
                                    .add(cabaz)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "Offer added with ID: " + documentReference.getId());
                                            Log.d(TAG, "Offer data: "+cabaz);
                                            //Toast and redirect backwards
                                            Toast.makeText(applicationContext,"Offer added with success",Toast.LENGTH_LONG).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                            Toast.makeText(applicationContext,"Error adding offer,please check your connection",Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                        else{
                            Log.e(TAG,"Cant query user to get city");
                        }
                    }
                });
    }
}
