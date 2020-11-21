package com.example.icmproject.authentification;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.icmproject.R;
import com.example.icmproject.location.FetchAddressTask;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterRestaurantActivity extends AppCompatActivity implements FetchAddressTask.OnTaskCompleted {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "registerRestActivity";
    private FusedLocationProviderClient locationClient;
    private Location locationGotten;
    private boolean loadingLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);
        mAuth = FirebaseAuth.getInstance();
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        EditText loc = (EditText)findViewById(R.id.editTextLocationRegisterRestaurant);
        loc.setInputType(InputType.TYPE_NULL);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
        else {
            Log.d(TAG, "getLocation: permissions already granted");
            locationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                locationGotten = location;
                                new FetchAddressTask(RegisterRestaurantActivity.this,RegisterRestaurantActivity.this::onTaskCompleted).execute(location);
                                Log.d(TAG, locationGotten.toString());
                            } else {
                                Log.e(TAG, "No Location could be Retrieved");
                            }
                        }
                    });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                }
                else {
                    //Do Stuff here if cant get Location
                    Log.d(TAG,"Location permission denied");
                }
                break;
        }
    }
    public void submitRegisterRestaurant(View view) {
        String email = ((TextView)findViewById(R.id.editTextEmailRegisterRestaurant)).getText().toString();
        String password = ((TextView)findViewById(R.id.editTextPasswordRegisterRestaurant)).getText().toString();
        String local = ((TextView)findViewById(R.id.editTextLocationRegisterRestaurant)).getText().toString();
        String res_name = ((TextView)findViewById(R.id.editTextRestaurantNameRegisterRestaurant)).getText().toString();
        if(locationGotten == null){
            Toast.makeText(this,"Please accept location permission and wait until we fetch your adddress",Toast.LENGTH_LONG).show();
            return;
        }
        registerUser(email,password,local,res_name);
    }

    private void registerUser(String email,String password,String localization,String res_name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Redirect to DB stuff
                            addRestaurantToDB(email,password,localization,res_name,user.getUid(),locationGotten);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterRestaurantActivity.this, "Authentication failed,please try again",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addRestaurantToDB(String email,String password,String city,String rest_name,String uid,Location coord) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", "restaurant");
        res.put("email", email);
        res.put("password", password);
        res.put("city",city);
        res.put("restaurant_name",rest_name);
        res.put("location",coord);
        // Add a new restaurant with a generated ID
        db.collection("users")
                .document(uid)
                .set(res)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + uid);
                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.e(TAG,"User deleted after database failure");
                            }
                        });
                        Log.w(TAG, "Error adding document", e);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        Log.w(TAG, "Document added");
                    }
                });
    }

    @Override
    public void onTaskCompleted(List<Address> result) {
        Log.d(TAG,"Got Address via geocoder"+result.get(0).toString());
        //É isto que se quer,Distrito,result.get(0).getAdminArea()
        //Locality para cidade result.get(0).getLocality()
        EditText loc = (EditText)findViewById(R.id.editTextLocationRegisterRestaurant);
        loc.setText(result.get(0).getLocality());
    }

    public void enterLocationOnClick(View view) {
        EditText locationBox = (EditText)view;
        //Lock box
        locationBox.setInputType(InputType.TYPE_NULL);
        Toast.makeText(this,"Fetching location...",Toast.LENGTH_SHORT).show();
        getLocation();
    }
}