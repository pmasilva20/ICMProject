package com.example.icmproject.client.Model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Location;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModel;

import com.example.icmproject.commonDataModel.Offer;
import com.example.icmproject.location.FetchAddressTask;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ClientSeeOfferViewModel extends ViewModel implements FetchAddressTask.OnTaskCompleted{

    private static final String TAG = "clientSeeOfferVM";
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private List<Offer> offersList;
    private List<String> cityList;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ClientCityListAdapter adapter;
    private ClientSeeOfferAdapter adapterOffer;
    private Location locationGotten;
    private FusedLocationProviderClient locationClient;
    private List<Button> reserveButtonList;

    public ClientSeeOfferViewModel() {
        this.offersList = new ArrayList<>();
        this.cityList = new ArrayList<>(Arrays.asList("Viana do Castelo","Braga","Vila Real","Bragança","Porto"
                ,"Aveiro","Viseu","Guarda","Coimbra","Castelo Branco","Leiria","Santarém","Portalegre",
                "Lisboa","Setubal","Évora","Beja","Faro"));
        this.reserveButtonList = new ArrayList<>();
    }

    public List<Offer> getListOffers(){
        return offersList;
    }

    public void loadOffersForClient(ClientSeeOfferAdapter adapter){
        offersList.clear();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser loggedIn = mAuth.getCurrentUser();

        db.collection("offers").whereEqualTo("cityCreator",this.adapter.citySelected).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                            Offer of = doc.toObject(Offer.class);
                            of.setDbId(doc.getId());
                            offersList.add(of);
                        }
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if (adapterOffer != null)adapterOffer.notifyDataSetChanged();
                        }
                    }
                });
    }
    public void getCityAddress(Activity act, Context con){
        locationClient = LocationServices.getFusedLocationProviderClient(act);
        getLocation(act,con);
    }

    private void getLocation(Activity act,Context con) {
        if (ActivityCompat.checkSelfPermission(con,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
        locationClient.getLastLocation().addOnSuccessListener(
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            locationGotten = location;
                            new FetchAddressTask(con, ClientSeeOfferViewModel.this).execute(location);
                        }
                    }
                });
    }

    public List<String> getCityList() {
        return cityList;
    }

    @Override
    public void onTaskCompleted(List<Address> result) {
        if(!cityList.contains(result.get(0).getLocality())){
            cityList.add(result.get(0).getLocality());
            adapter.notifyDataSetChanged();
        }
        adapter.citySelected = result.get(0).getLocality();

    }


    public void setAdapterCity(ClientCityListAdapter adapterCity) {
        adapter = adapterCity;
    }
    public void setAdapterOffer(ClientSeeOfferAdapter adapterOffer) {
        this.adapterOffer = adapterOffer;
    }

    public void sendRequest(String dbId, View v) {
        db.collection("users").document(mAuth.getCurrentUser().getUid())
                .update("requestedOffers", FieldValue.arrayUnion(dbId));
        db.collection("offers").document(dbId)
                .update("requestedBy", FieldValue.arrayUnion(mAuth.getCurrentUser().getUid()));
        //Update UI
        updateReserveButtonsUI((Button) v);
    }

    private void updateReserveButtonsUI(Button button) {
        //Lock and grey out
        button.setEnabled(false);
        button.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        button.setClickable(false);
    }

    public void addReserveButtonList(Button reserveButton) {

        reserveButtonList.add(reserveButton);
    }

    public void checkIfRequestedAlready(Offer current, Button reserveButton) {
        db.collection("users").document(mAuth.getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            //Check for offer in requestedOffers,if yes,grey button
                            Map<String, Object> map =  task.getResult().getData();
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                if (entry.getKey().equals("requestedOffers")) {
                                    List<String> offerList = (List<String>) entry.getValue();
                                    for(String offer : offerList){
                                        if(offer.equals(current.getDbId())){
                                            updateReserveButtonsUI(reserveButton);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
    }
}

