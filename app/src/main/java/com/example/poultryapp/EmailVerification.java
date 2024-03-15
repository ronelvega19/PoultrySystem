package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.util.Objects;

public class EmailVerification extends AppCompatActivity {

        EditText email;
        Button confirm;
    FirebaseAuth mAuth;
    FirebaseAuth userAuth;
    DatabaseReference userAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        confirm = findViewById(R.id.otpemailBTN);


        verify();
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            // Handle the deep link, extract verification parameters if needed
                            // Check if email is verified and redirect user accordingly
                            // For example, you can use Firebase Authentication to check email verification status
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                           new Handler().postDelayed(
                                   new Runnable() {
                                       @Override
                                       public void run() {
                                           startActivity(new Intent(EmailVerification.this,SignIn.class));
                                       }
                                   },1000
                           );
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle errors
                        Log.w("Link", "getDynamicLink:onFailure", e);
                    }
                });

    }

    public void verify(){
        mAuth = FirebaseAuth.getInstance();
        userAuth = FirebaseAuth.getInstance();
        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences datas = getSharedPreferences("myPref", Context.MODE_PRIVATE);
                        String emails = datas.getString("username","");
                        String passs =  datas.getString("password","");
                        Log.d("Email&PASS", emails+passs);
                        // Create a new user with email and password
                        mAuth.createUserWithEmailAndPassword(String.valueOf(emails), String.valueOf(passs))
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Registration successful, update UI accordingly

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            user.sendEmailVerification().addOnCompleteListener(
                                                    new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                SharedPreferences datas = getSharedPreferences("myPref", Context.MODE_PRIVATE);
                                                                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                                                RegistrationData data = new RegistrationData(datas.getString("username","")
                                                                        ,datas.getString("password",""),
                                                                        datas.getString("number",""),
                                                                        datas.getString("fName",""),
                                                                        datas.getString("lName",""));
                                                                userAccount = FirebaseDatabase.getInstance().getReference().child("Users");
                                                                userAccount.push().setValue(data);

                                                                new Handler().postDelayed(
                                                                        new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                startActivity(new Intent(EmailVerification.this,SignIn.class));
                                                                            }
                                                                        },1000
                                                                );
                                                            }else{
                                                                Toast.makeText(EmailVerification.this, "Failed", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }
                                            );
                                        } else {
                                            // Registration failed, display a message to the user
                                            Log.d("EmailError", task.getException().getMessage());

                                        }
                                    }
                                });

                    }
                }
        );
    }
}