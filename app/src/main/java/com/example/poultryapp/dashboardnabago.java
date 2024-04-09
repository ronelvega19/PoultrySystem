package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dashboardnabago extends AppCompatActivity {
    TextView humidityValue, tempValue, carbonValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        setContentView(R.layout.activity_dashboardnabago);
        humidityValue = findViewById(R.id.humidityPercent);
        tempValue = findViewById(R.id.temperaturePercent);
        carbonValue = findViewById(R.id.airQualityPercent);
        ImageView bur;

        bur = findViewById(R.id.burger);


        bur.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(dashboardnabago.this, ProfileSettings.class));
                    }
                }
        );
        displayData();

    }
        DatabaseReference value;
    private void displayData(){
        value = FirebaseDatabase.getInstance().getReference();

        value.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int tValue = Integer.parseInt(String.valueOf(snapshot.child("temperature").getValue()));
                        int cValue = Integer.parseInt(String.valueOf(snapshot.child("carbonDioxide").getValue()));
                        int hValue = Integer.parseInt(String.valueOf(snapshot.child("humidity").getValue()));
                        tempValue.setText(String.format("%d°C", tValue));
                        carbonValue.setText(String.format("%dPPM", cValue));
                        humidityValue.setText(String.format("%d%%", hValue));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

    }





}