package com.example.poultryapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {

    EditText min, max, air, fan, light, water, servo;
    Button feed, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        min = findViewById(R.id.passFill);
        max = findViewById(R.id.passFilll);
        air = findViewById(R.id.editText5);
        fan = findViewById(R.id.editText);
        light = findViewById(R.id.editText3);
        water = findViewById(R.id.editText31);
        servo = findViewById(R.id.editText3z1s);
        feed = findViewById(R.id.timeButton);
        save = findViewById(R.id.saveChanges);

        display();
    }
    DatabaseReference settings;

    DatabaseReference rd;
    FirebaseAuth userData;

    private void display() {

        userData = FirebaseAuth.getInstance();
        rd = FirebaseDatabase.getInstance().getReference().child("Settings");

        rd.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            min.setText(String.valueOf(snapshot.child("Temperature").child("Min").getValue()));
                            max.setText(String.valueOf(snapshot.child("Temperature").child("Max").getValue()));
                            air.setText(String.valueOf(snapshot.child("Carbon").child("Min").getValue()));
                            fan.setText(String.valueOf(snapshot.child("Fan").child("Time").getValue()));
                            light.setText(String.valueOf(snapshot.child("Light").child("Time").getValue()));
                            water.setText(String.valueOf(snapshot.child("Fan").child("Time").getValue()));
                            servo.setText(String.valueOf(snapshot.child("Servo").child("Time").getValue()));
                            //water.setText(String.valueOf(snapshot.child("Fan").child("Time").getValue()));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }

        );

        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Settings");
                        settings = FirebaseDatabase.getInstance().getReference().child("Settings");
                        settings.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snap : snapshot.getChildren()) {
                                            userRef.child("Temperature").child("Min").setValue(String.valueOf(min.getText()));
                                            userRef.child("Temperature").child("Max").setValue(String.valueOf(max.getText()));
                                            userRef.child("Carbon").child("Min").setValue(String.valueOf(air.getText()));
                                            userRef.child("Fan").child("Time").setValue(String.valueOf(fan.getText()));
                                            userRef.child("Light").child("Time").setValue(String.valueOf(light.getText()));
                                            userRef.child("Water").child("Time").setValue(String.valueOf(water.getText()));
                                            userRef.child("Servo").child("Time").setValue(String.valueOf(servo.getText()));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                }
                        );
                        
                    }
                }
        );
    }
}