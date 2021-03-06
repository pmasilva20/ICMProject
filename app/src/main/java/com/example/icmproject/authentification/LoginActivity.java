package com.example.icmproject.authentification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.icmproject.R;
import com.example.icmproject.client.UI.ClientMenuActivity;
import com.example.icmproject.restaurant.restaurantSeeOffer.UI.RestaurantSeeOffersActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;
    private static final String TAG = "loginActivity";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        //Authentification
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void redirectUser(FirebaseUser currentUser) {
        //Already logged in
        db.collection("users")
                .whereEqualTo("email",currentUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Send token for user just logged in
                                sendNotificationToken(currentUser.getUid());
                                Map<String,Object> userData = document.getData();
                                if(((String)userData.get("status")).equals("client")){
                                    Intent i = new Intent(getApplicationContext(), ClientMenuActivity.class);
                                    startActivity(i);
                                }
                                else{
                                    Intent i = new Intent(getApplicationContext(), RestaurantSeeOffersActivity.class);
                                    startActivity(i);
                                }
                            }
                        }
                    }
                });
    }

    private void sendNotificationToken(String uid) {
        //Fetch from FireBase Messaging,send to FireStore
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        //Send to FS
                        Map<String,Object> insert = new HashMap<>();
                        insert.put("notificationToken",token);
                        db.collection("users").document(uid).update(insert);

                    }
                });
    }


    private void loginUser(String email,String password) {
        //Login
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            redirectUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void loginAccount(View view) {
        //Ask info for log in
        //Try to log in and then redirect user

        TextInputLayout textInputLayoutEmail = findViewById(R.id.editTextEmailAddressLogin);
        String email = textInputLayoutEmail.getEditText().getText().toString();

        TextInputLayout textInputLayoutPassword = findViewById(R.id.editTextPasswordLogin);
        String password = textInputLayoutPassword.getEditText().getText().toString();
        loginUser(email,password);
    }
}