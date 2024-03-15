package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

        TextView user, pass;
        Button check;
    FirebaseAuth users;
        DatabaseReference logins;
        boolean isMatch=false;
        FirebaseAuth mAuth;
    Intent intent;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        user = findViewById(R.id.usernameTXT);
        pass = findViewById(R.id.passwordTXT);
        check = findViewById(R.id.logs);

        check.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logins = FirebaseDatabase.getInstance().getReference().child("Users");
                        String username = user.getText().toString();
                        String password = pass.getText().toString();
                        if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password)){
                            Toast.makeText(SignIn.this, "Please fill the forms", Toast.LENGTH_SHORT).show();
                        }else{
                            mAuth = FirebaseAuth.getInstance();

                            mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(
                                    new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                if(mAuth.getCurrentUser().isEmailVerified()){
                                                    intent = new Intent(SignIn.this, dashboard.class);

                                                    startActivity(intent);

                                                }

                                            }else{
                                                Toast.makeText(SignIn.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                            );

                        }
                    }
                }
        );

    }
}