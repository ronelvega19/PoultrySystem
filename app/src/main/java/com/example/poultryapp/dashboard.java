package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class dashboard extends AppCompatActivity {
    TextView percent,air,humid,dates,times;
    DatabaseReference temp;
    Switch lights, waters,feeds,fans;
    // Initialize Firebase
    DatabaseReference statusRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    private Handler handler;
    private Runnable updateTimeTask;
    ImageView burger;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        percent = findViewById(R.id.percentTemperature);
        air = findViewById(R.id.percentAirquantity);
        humid = findViewById(R.id.percentHumidity);
        lights = findViewById(R.id.lightSwitch);
        waters = findViewById(R.id.PumpSwitch);
        feeds = findViewById(R.id.FeedingSwitch);
        fans = findViewById(R.id.fanSwitch);
        dates = findViewById(R.id.dateTV);
        times = findViewById(R.id.timeTV);
        burger = findViewById(R.id.burgerImage);





        statActivation();
        displayVal();
        time();

        burger.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(dashboard.this, ProfileSettings.class);
                        intent.putExtra("username",getIntent().getStringExtra("username"));
                        intent.putExtra("password",getIntent().getStringExtra("password"));
                        intent.putExtra("number",getIntent().getStringExtra("number"));
                        startActivity(intent);
                    }
                }
        );
    }
DatabaseReference CurrentTime;
    private void time(){
        handler = new Handler();
        updateTimeTask = new Runnable() {
            @Override
            public void run() {
                // Get current date and time
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
                String currentDateTime = dateFormat.format(calendar.getTime());
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
                String currentDateTime2 = dateFormat2.format(calendar.getTime());
                SimpleDateFormat dateFormat3 = new SimpleDateFormat("hh:mm", Locale.getDefault());
                String currentDateTime3 = dateFormat3.format(calendar.getTime());

                SimpleDateFormat dateFormat4 = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
                String currentDateTime4 = dateFormat4.format(calendar.getTime());

                // Set text to TextView
               dates.setText(currentDateTime.toUpperCase());
               times.setText(currentDateTime2.toUpperCase());
                CurrentTime = FirebaseDatabase.getInstance().getReference().child("Time");
                CurrentTime.child("CurrentTime").setValue(currentDateTime3);
                CurrentTime.child("CurrentDate").setValue(currentDateTime4);

                // Call this runnable again after a delay (e.g., every second)
                handler.postDelayed(this, 1000); // 1000 milliseconds = 1 second
            }
        };
        handler.post(updateTimeTask);
    }




    private void statActivation(){
        statusRef = database.getReference("Status");
        lights.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        statusRef.child("LIGHT").setValue(isChecked ? 1 : 0);
                    }
                }
        );
        waters.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        statusRef.child("WATER").setValue(isChecked ? 1 : 0);
                    }
                }
        );
        fans.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        statusRef.child("FAN").setValue(isChecked ? 1 : 0);
                    }
                }
        );
        feeds.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        statusRef.child("FEED").setValue(isChecked ? 1 : 0);
                    }
                }
        );
    }
    private void displayVal(){
        temp = FirebaseDatabase.getInstance().getReference();
        temp.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        percent.setText(snapshot.child("temperature").getValue().toString()+"°C");
                        air.setText(snapshot.child("carbonDioxide").getValue().toString()+"PPM");
                        humid.setText(snapshot.child("humidity").getValue().toString()+"%");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

        DatabaseReference stat = FirebaseDatabase.getInstance().getReference().child("Status");
        stat.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int fan = Integer.parseInt(String.valueOf(snapshot.child("FAN").getValue()));
                        int water = Integer.parseInt(String.valueOf(snapshot.child("WATER").getValue()));
                        int feed = Integer.parseInt(String.valueOf(snapshot.child("FEED").getValue()));
                        int light = Integer.parseInt(String.valueOf(snapshot.child("LIGHT").getValue()));
                        if(fan==1) {
                            fans.setChecked(true);
                        }else {
                            fans.setChecked(false);
                        }
                        if(water==1) {
                            waters.setChecked(true);
                        }else {
                            waters.setChecked(false);
                        }
                        if(feed==1) {
                            feeds.setChecked(true);
                        }else {
                            feeds.setChecked(false);
                        }
                        if(light==1) {
                            lights.setChecked(true);
                        }else {
                            lights.setChecked(false);
                        }
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );









    }
}