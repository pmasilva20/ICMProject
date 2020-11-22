package com.example.icmproject.restaurantSeeOffer;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.icmproject.Offer;
import com.example.icmproject.Product;
import com.example.icmproject.UserView;
import com.example.icmproject.notification.NotificationManager;
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

    public void confirmRequest(String userUid){
        //I have user ID,get from here user notification Token,generate a key for this Offer and send notification
        db.collection("users").document(userUid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            String token = (String)task.getResult().get("notificationToken");
                            String keyGenerated = generateKey(offerSelected);
                            //String receiverToken,String title,String body,String dataValue
                            String body = String.format("O teu pedido de valor %s foi confirmado com sucesso\n" +
                                    "O teu código de validação é o seguinte:%s\n" +
                                    "Por favor mostra isto ao ir buscar a entrega",offerSelected.getPrice(),keyGenerated);
                            new NotificationManager().execute(token,"O teu pedido foi confirmado!",body,"");
                            //Put key in DB
                            db.collection("offers").document(offerSelected.getDbId()).
                                    update("confirmationKey",keyGenerated);
                        }
                    }
                });
    }

    private String generateKey(Offer offer) {
        String key = String.valueOf(offer.hashCode());
        Log.d(TAG,"Gnerating key for Offer:"+offer.getDbId()+ " key:"+key);
        return key;
    }

    public List<UserView> getUsers(){
        return usersWhoRequested;
    }

    public List<Product> getListProductsForOffer() {
        return offerSelected.getProducts();
    }
}
