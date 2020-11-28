package com.example.icmproject.authentification;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);
        mAuth = FirebaseAuth.getInstance();
        locationClient = LocationServices.getFusedLocationProviderClient(this);

        TextInputEditText loc = (TextInputEditText)findViewById(R.id.editTextLocationRegisterRestaurant);
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
            locationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                locationGotten = location;
                                new FetchAddressTask(RegisterRestaurantActivity.this,RegisterRestaurantActivity.this::onTaskCompleted).execute(location);
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
                break;
        }
    }
    public void submitRegisterRestaurant(View view) {

        TextInputLayout textInputLayoutEmail = findViewById(R.id.editTextEmailRegisterRestaurant);
        String email = textInputLayoutEmail.getEditText().getText().toString();

        TextInputLayout textInputLayoutPassword = findViewById(R.id.editTextPasswordRegisterRestaurant);
        String password = textInputLayoutPassword.getEditText().getText().toString();

        TextInputLayout textInputLayoutName = findViewById(R.id.editTextRestaurantNameRegisterRestaurant);
        String res_name = textInputLayoutName.getEditText().getText().toString();

        TextInputLayout textInputLayoutConfirmPassword = findViewById(R.id.editTextConfirmPasswordRegisterRestaurant);
        String confirmPassword = textInputLayoutConfirmPassword.getEditText().getText().toString();

        String local = ((TextInputEditText)findViewById(R.id.editTextLocationRegisterRestaurant)).getText().toString();


        if (!password.equals(confirmPassword)){
            Toast.makeText(this,"Passwords don't match", Toast.LENGTH_SHORT).show();
        }
        else if(locationGotten == null){
            Toast.makeText(this,"Please accept location permission and wait until we fetch your adddress",Toast.LENGTH_LONG).show();
            return;
        }
        else {
            registerUser(email,password,local,res_name);
        }


    }

    private void registerUser(String email,String password,String localization,String res_name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Redirect to DB stuff
                            addRestaurantToDB(email,password,localization,res_name,user.getUid(),locationGotten);
                        } else {
                            // If sign in fails, display a message to the user.
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
                            }
                        });
                    }
                })
                .addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                    }
                });
    }

    @Override
    public void onTaskCompleted(List<Address> result) {
        TextInputEditText loc = (TextInputEditText)findViewById(R.id.editTextLocationRegisterRestaurant);
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