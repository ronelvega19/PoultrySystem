package com.example.poultryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProfileSettings extends AppCompatActivity {

        TextView name,emailProfile;
        RelativeLayout accountDetailsGROUP,signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        name = findViewById(R.id.nameProfile);
        emailProfile = findViewById(R.id.emailProfile);
        accountDetailsGROUP = findViewById(R.id.accountDetailsGROUP);
        signout = findViewById(R.id.signoutGroup);
        displayDATA();
        settings();

        signout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(ProfileSettings.this,SignIn.class));
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
    public void displayDATA(){
        SharedPreferences datas = getSharedPreferences("myPref", Context.MODE_PRIVATE);

        name.setText(datas.getString("fName","")+" "+datas.getString("lName",""));
        emailProfile.setText(datas.getString("username",""));
    }
}