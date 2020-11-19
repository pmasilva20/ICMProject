package com.example.icmproject.authentification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.icmproject.R;
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
import java.util.Map;

public class RegisterRestaurantActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "registerRestActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);
        mAuth = FirebaseAuth.getInstance();
    }

    public void submitRegisterRestaurant(View view) {
        String email = ((TextView)findViewById(R.id.editTextEmailRegisterRestaurant)).getText().toString();
        String password = ((TextView)findViewById(R.id.editTextPasswordRegisterRestaurant)).getText().toString();
        String local = ((TextView)findViewById(R.id.editTextLocationRegisterRestaurant)).getText().toString();
        String res_name = ((TextView)findViewById(R.id.editTextRestaurantNameRegisterRestaurant)).getText().toString();
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
                            addRestaurantToDB(email,password,localization,res_name,user.getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterRestaurantActivity.this, "Authentication failed,please try again",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addRestaurantToDB(String email,String password,String local,String rest_name,String uid) {
        Map<String, Object> res = new HashMap<>();
        res.put("status", "restaurant");
        res.put("email", email);
        res.put("password", password);
        res.put("localization",local);
        res.put("restaurant_name",rest_name);
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
}