package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileSettings extends AppCompatActivity {

        TextView name,emailProfile;
        ImageView back;
        RelativeLayout accountDetailsGROUP,signout,cpass,notificationGroup,settingsGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        name = findViewById(R.id.nameProfile);
        settingsGroup = findViewById(R.id.settingsGroup);
        emailProfile = findViewById(R.id.emailProfile);
        accountDetailsGROUP = findViewById(R.id.accountDetailsGROUP);
        notificationGroup = findViewById(R.id.notificationGroup);
        signout = findViewById(R.id.signoutGroup);

        cpass = findViewById(R.id.changepassGroup);

        back = findViewById(R.id.back4);

        displayDATA();
        settings();
        settingsGroup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ProfileSettings.this,newSettings.class));

                    }
                }
        );
        cpass.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ProfileSettings.this, changepassword.class));

                    }
                }
        );



        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ProfileSettings.this, dashboardnabago.class));

                    }
                }
        );

        
        signout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(ProfileSettings.this,SignIn.class));
                                    new ActivityLogs().addLog("signed out");
                    }
                }
        );

        notificationGroup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ProfileSettings.this, Notification.class));

                    }
                }
        );
    }
    public void settings(){
        accountDetailsGROUP.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(ProfileSettings.this, AccountDetails.class);
                        startActivity(i);
                    }
                }
        );
    }
    FirebaseAuth userData;
    DatabaseReference rd;
    public void displayDATA(){
       userData = FirebaseAuth.getInstance();
       rd = FirebaseDatabase.getInstance().getReference().child("Users");
       String email = String.valueOf(userData.getCurrentUser().getEmail());
        rd.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(String.valueOf(snap.child("email").getValue()).equals(email)){
                                name.setText(String.valueOf(snap.child("first_name").getValue())+" "+String.valueOf(snap.child("last_name").getValue()));
                                emailProfile.setText(String.valueOf(snap.child("email").getValue()));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );


    }
}