package com.example.icmproject.restaurant.restaurantSeeOffer.Model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModel;

import com.example.icmproject.commonDataModel.Offer;
import com.example.icmproject.commonDataModel.Product;
import com.example.icmproject.R;
import com.example.icmproject.commonDataModel.UserView;
import com.example.icmproject.notification.NotificationManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OfferDetailsViewModel extends ViewModel{

    private static final String TAG = "offerDetailsViewModel";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Offer offerSelected;
    private List<Product> productsInOfferSelected;
    private List<String> idsUsersWhoRequested = new ArrayList<>();
    private List<UserView> usersWhoRequested = new ArrayList<>();
    private List<View> userViews = new ArrayList<>();
    private View tokenInputView;
    private View tokenButtonView;
    private OfferDetailsViewModel vm = this;


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

    public void confirmRequest(String userUid, View view, Context context){
        //I have user ID,get from here user notification Token,generate a key for this Offer and send notification
        db.collection("users").document(userUid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            String token = (String)task.getResult().get("notificationToken");
                            String keyGenerated = generateKey(offerSelected);
                            db.collection("users").document(offerSelected.madeBy).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    String body;
                                    if(task.isSuccessful()){
                                        String rest_name = (String) task.getResult().get("restaurant_name");
                                        //String receiverToken,String title,String body,String dataValue
                                        body = String.format("Your order of value %s€ has been confirmed with success\n" +
                                                "Your validation key is the following:%s\n" +
                                                "Please show this key when you get your order at the restaurant %s",offerSelected.getPrice(),keyGenerated,rest_name);
                                    }
                                    else {
                                        //String receiverToken,String title,String body,String dataValue
                                        body = String.format("Your order of value %s€ has been confirmed with success\n" +
                                                "Your validation key is the following:%s\n" +
                                                "Please show this key when you get your order",offerSelected.getPrice(),keyGenerated);
                                    }
                                    new NotificationManager().execute(token,"Your order has been confirmed!",body,"");
                                    //Put key in DB
                                    db.collection("offers").document(offerSelected.getDbId()).
                                            update("confirmationKey",keyGenerated,"confirmedUser",userUid);
                                    //Update UI
                                    Log.d(TAG,"Gonna update UI now");
                                    updateUIConfirmedUser();
                                }
                            });
                        }
                        else{
                            Toast.makeText(context,"Error trying to confirm sent key,please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void addUserView(View v){
        userViews.add(v);
    }
    public void setTokenInputView(View v){
        tokenInputView = v;
    }
    public void setTokenButtonView(View v){
        tokenButtonView = v;
    }
    private void updateUIConfirmedUser() {
        Log.d(TAG,"Update occuring");
        for(View v : userViews){
            //Lock all of them and grey them out
            CardView holder = (CardView)v;
            Button button = (Button)holder.findViewById(R.id.buttonConfirmRequest);
            button.setEnabled(false);
            button.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            button.setClickable(false);
        }
        tokenInputView.setVisibility(View.VISIBLE);
        tokenButtonView.setVisibility(View.VISIBLE);

    }

    private String generateKey(Offer offer) {
        String key = String.valueOf(Math.abs(offer.hashCode()) % 100000);
        Log.d(TAG,"Generating key for Offer:"+offer.getDbId()+ " key:"+key);
        return key;
    }

    public List<UserView> getUsers(){
        return usersWhoRequested;
    }

    public List<Product> getListProductsForOffer() {
        return offerSelected.getProducts();
    }

    public void checkUpdateUI() {
        db.collection("offers").document(offerSelected.getDbId()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().get("confirmedUser") != null){
                                Log.d(TAG,"Update caused by already confirmed");
                                //Already confirmed grey out buttons
                                updateUIConfirmedUser();
                            }
                        }
                    }
                });
    }

    public void checKeyAuth(Context contect) {
        String input = ((EditText)tokenInputView).getText().toString();
        Log.d(TAG,"Checking for key:"+input);
        db.collection("offers").document(offerSelected.getDbId()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            String keyGotten = (String) task.getResult().get("confirmationKey");
                            if (input.equals(keyGotten)){
                                //Key checks out
                                //TODO:Maybe change to take it out?

                                db.collection("offers").document(offerSelected.getDbId())
                                        .update("offerConfirmed",true);
                                Log.d(TAG,"Updated offer to be confirmed");
                                vm.checkUpdateUI();
                                for(UserView user : usersWhoRequested) {
                                    sendOfferConfirmedToRemainingUsers(user.getDbId());
                                }

                            }
                            else{
                                Toast.makeText(contect,"Key given is invalid,please try again",Toast.LENGTH_LONG);
                            }
                        }
                    }
                });
    }


private void sendOfferConfirmedToRemainingUsers(String userUid){

    db.collection("users").document(userUid).get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        String token = (String)task.getResult().get("notificationToken");
                        //String receiverToken,String title,String body,String dataValue
                        String body = String.format("We are very sorry,but it seems the offer you requested is" +
                                " no longer available");
                        new NotificationManager().execute(token,"Canceled order",body,"");
                        Log.d(TAG,"Users who requested were notified");
                    }
                }
            });
    }
}