package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dashboardnabago extends AppCompatActivity {
    TextView humidityValue, tempValue, carbonValue;
    Switch lightSwitch, fanSwitch, pumpSwitch,feedSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        setContentView(R.layout.activity_dashboardnabago);
        humidityValue = findViewById(R.id.humidityPercent);
        tempValue = findViewById(R.id.temperaturePercent);
        carbonValue = findViewById(R.id.airQualityPercent);
        lightSwitch = findViewById(R.id.lightSwitch);
        fanSwitch = findViewById(R.id.fanSwitch);
        pumpSwitch = findViewById(R.id.pumpSwitch);
        feedSwitch = findViewById(R.id.feedSwitch);
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
        systemSwitch();
        startActivation();

    }
        DatabaseReference value;
    private void displayData(){
        value = FirebaseDatabase.getInstance().getReference();

        value.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        tempValue.setText(String.format("%d°C", Math.round(snapshot.child("temperature").getValue(Float.class))));
                        carbonValue.setText(String.format("%dPPM", Math.round(snapshot.child("carbonDioxide").getValue(Float.class))));
                        humidityValue.setText(String.format("%d%%", Math.round(snapshot.child("humidity").getValue(Float.class))));
                    }




                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

    }
        private void systemSwitch(){
            DatabaseReference stat = FirebaseDatabase.getInstance().getReference().child("Status");
            stat.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int fan = Integer.parseInt(String.valueOf(snapshot.child("FAN").getValue()));
                            int water = Integer.parseInt(String.valueOf(snapshot.child("WATER").getValue()));
                            int feed = Integer.parseInt(String.valueOf(snapshot.child("FEED").getValue()));
                            int light = Integer.parseInt(String.valueOf(snapshot.child("LIGHT").getValue()));
                            fanSwitch.setChecked(fan==1?true:false);
                            lightSwitch.setChecked(light==1?true:false);
                            pumpSwitch.setChecked(water==1?true:false);
                            feedSwitch.setChecked(feed==1?true:false);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );
        }
    DatabaseReference statusRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private void startActivation(){
        statusRef = database.getReference("Status");
        lightSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        statusRef.child("LIGHT").setValue(isChecked ? 1 : 0);
                    }
                }
        );
        pumpSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        statusRef.child("WATER").setValue(isChecked ? 1 : 0);
                    }
                }
        );
        fanSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        statusRef.child("FAN").setValue(isChecked ? 1 : 0);
                    }
                }
        );
        feedSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        statusRef.child("FEED").setValue(isChecked ? 1 : 0);
                    }
                }
        );
    }




}