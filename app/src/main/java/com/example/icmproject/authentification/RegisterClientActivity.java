package com.example.icmproject.authentification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.icmproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Redirect to DB stuff
                            addClientToDB(email,password,user.getUid());
                        } else {
                            // If sign in fails, display a message to the user.
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
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
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


        if (!password.equals(confirmPassword)){
            Toast.makeText(this,"Passwords don't match", Toast.LENGTH_SHORT).show();
        }

        else {
            registerUser(email,password);
        }

    }
}