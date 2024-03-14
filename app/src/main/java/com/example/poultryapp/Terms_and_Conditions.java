package com.example.poultryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.net.InetSocketAddress;

public class Terms_and_Conditions extends AppCompatActivity {
    Button accept, decline;
    CheckBox agree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);

        accept = findViewById(R.id.acceptBTN);
        decline = findViewById(R.id.declineBTN);
        agree = findViewById(R.id.agreeCB);
        String user = getIntent().getStringExtra("username");
        String pass = getIntent().getStringExtra("password");
        accept.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        agree.setChecked(true);
                        Intent intent = new Intent(Terms_and_Conditions.this,SMSVerification.class);
                        intent.putExtra("username",user);
                        intent.putExtra("password",pass);
                        startActivity(intent);
                    }
                }
        );
        decline.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Terms_and_Conditions.this,MainActivity.class));
                    }
                }
        );



    }
}