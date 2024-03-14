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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

        TextView user, pass;
        Button check;
        DatabaseReference logins;
        boolean isMatch=false;
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

                            logins.addValueEventListener(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                            for(DataSnapshot snap: snapshot.getChildren()){

                                                String Fuser = String.valueOf(snap.child("email").getValue());
                                                String Fpass = String.valueOf(snap.child("password").getValue());
                                                String Fnum = String.valueOf(snap.child("number").getValue());
                                                String Fname = String.valueOf(snap.child("first_name").getValue());
                                                String Lname = String.valueOf(snap.child("last_name").getValue());

                                                if((Fuser.equals(username) && Fpass.equals(password)) || (Fnum.equals(username) && Fpass.equals(password))){
                                                    isMatch=true;
                                                    intent = new Intent(SignIn.this,  dashboard.class);
                                                    intent.putExtra("username",Fuser);
                                                    intent.putExtra("password",Fpass);
                                                    intent.putExtra("number",Fnum);
                                                    SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPref.edit();
                                                    editor.putString("username",Fuser);
                                                    editor.putString("password",Fpass);
                                                    editor.putString("number",Fnum);
                                                    editor.putString("fName",Fname);
                                                    editor.putString("lName",Lname);
                                                    editor.apply();
                                                    break;
                                                }
                                            }
                                            if(isMatch){


                                                startActivity(intent);
                                                isMatch=false;
                                            }else{
                                                user.setTextColor(Color.RED);
                                                pass.setTextColor(Color.RED);
                                                Toast.makeText(SignIn.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    }
                            );
                        }
                    }
                }
        );

    }
}