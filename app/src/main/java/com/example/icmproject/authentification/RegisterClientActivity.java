package com.example.icmproject.authentification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icmproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RegisterClientActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final String TAG = "registerClientActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);
        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser(String email,String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Redirect to DB stuff
                            addClientToDB(email,password,user.getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterClientActivity.this, "Authentication failed,please try again",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addClientToDB(String email,String password,String uid) {
        Map<String, Object> client = new HashMap<>();
        client.put("status", "client");
        client.put("email", email);
        client.put("password", password);
        client.put("requestedOffers", Collections.emptyList());
        // Add a new client with a generated ID
        db.collection("users")
                .document(uid)
                .set(client)
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
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.w(TAG, "Document added");
                    }
                });
    }


    public void registerClientOnClick(View view) {

        TextInputLayout textInputLayoutEmail = findViewById(R.id.editTextEmailRegisterClient);
        String email = textInputLayoutEmail.getEditText().getText().toString();

        TextInputLayout textInputLayoutPassword = findViewById(R.id.editTextPasswordRegisterClient);
        String password = textInputLayoutPassword.getEditText().getText().toString();

        TextInputLayout textInputLayoutConfirmPassword = findViewById(R.id.editTextConfirmPasswordRegisterClient);
        String confirmPassword = textInputLayoutConfirmPassword.getEditText().getText().toString();

        Log.d(TAG, email);
        Log.d(TAG, password);
        Log.d(TAG, confirmPassword);

        if (!password.equals(confirmPassword)){
            Toast.makeText(this,"Passwords don't match", Toast.LENGTH_SHORT).show();
        }

        else {
            registerUser(email,password);
        }

    }
}