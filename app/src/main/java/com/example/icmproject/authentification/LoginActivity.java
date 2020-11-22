package com.example.icmproject.authentification;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.icmproject.ClientMenuActivity;
import com.example.icmproject.R;
import com.example.icmproject.RestaurantMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
        //Testing authentification
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        //Authentification
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            redirectUser(currentUser);
        }
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
                                Log.d(TAG,"Logging in");
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Map<String,Object> userData = document.getData();
                                if(((String)userData.get("status")).equals("client")){
                                    Log.d(TAG,"Redirecting to client");
                                }
                                else{
                                    Log.d(TAG,"Redirecting to restaurant");
                                    Intent i = new Intent(getApplicationContext(), ClientMenuActivity.class);
                                    startActivity(i);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
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
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            redirectUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void loginAccount(View view) {
        //Ask info for log in
        //Try to log in and then redirect user
        String email = ((TextView)findViewById(R.id.editTextEmailAddressLogin)).getText().toString();
        String password = ((TextView)findViewById(R.id.editTextPasswordLogin)).getText().toString();
        loginUser(email,password);
    }
}