package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class   newSettings extends AppCompatActivity {


    EditText min, max, air, fan, light, water, servo;

    Button save;
    ImageView backk, a3;
    DatabaseReference check;

    TextView a1, a2;
    Switch switchh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_settings);

        min = findViewById(R.id.passFill);
        max = findViewById(R.id.passFilll);
        air = findViewById(R.id.airtext);
        fan = findViewById(R.id.fantext);
        light = findViewById(R.id.lighttext);
        water = findViewById(R.id.watertext);
        servo = findViewById(R.id.servotext);
        save = findViewById(R.id.saveChanges);
        backk = findViewById(R.id.back3);



        a1 = findViewById(R.id.textView);
        a2 = findViewById(R.id.textView2);
        a3 = findViewById(R.id.imageView);

        switchh = findViewById(R.id.switchhh);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(newSettings.this, ProfileSettings.class));
            }
        });

        display();
//        checkdata();

        switchh.setOnCheckedChangeListener(

                new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        DatabaseReference userRef2 = FirebaseDatabase.getInstance().getReference().child("Settings");
                        check = FirebaseDatabase.getInstance().getReference().child("Settings");
                        userRef2.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snap : snapshot.getChildren()) {
                                            if (!isChecked) {
                                                int checkNo = 0;
                                                userRef2.child("isAutomatic").setValue(checkNo);
                                                min.setClickable(false);
                                                min.setFocusable(false);
                                                max.setClickable(false);
                                                max.setFocusable(false);
                                                air.setClickable(false);
                                                air.setFocusable(false);
                                                fan.setClickable(false);
                                                fan.setFocusable(false);
                                                light.setClickable(false);
                                                light.setFocusable(false); }


                                            if (isChecked) {
                                                int checkYes = 1;
                                                userRef2.child("isAutomatic").setValue(checkYes);
                                                min.setClickable(true);
                                                min.setFocusable(true);
                                                max.setClickable(true);
                                                max.setFocusable(true);
                                                air.setClickable(true);
                                                air.setFocusable(true);
                                                fan.setClickable(true);
                                                fan.setFocusable(true);
                                                light.setClickable(true);
                                                light.setFocusable(true);

                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                }
                        );

                        //here

                    }

                }

        );

    }

    DatabaseReference settings, time;

    DatabaseReference rd;
    FirebaseAuth userData;
//    private void checkdata(){
//        DatabaseReference userRef2 = FirebaseDatabase.getInstance().getReference().child("Settings");
//        userRef2.addValueEventListener(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        int ch = snapshot.child("isAutomatic").getValue(Integer.class);
//                        if(ch==1){
//                            min.setClickable(true);
//                            min.setFocusable(true);
//                            max.setClickable(true);
//                            max.setFocusable(true);
//                            air.setClickable(true);
//                            air.setFocusable(true);
//                            fan.setClickable(true);
//                            fan.setFocusable(true);
//                            light.setClickable(true);
//                            light.setFocusable(true);
//                        }else{
//                            min.setClickable(false);
//                            min.setFocusable(false);
//                            max.setClickable(false);
//                            max.setFocusable(false);
//                            air.setClickable(false);
//                            air.setFocusable(false);
//                            fan.setClickable(false);
//                            fan.setFocusable(false);
//                            light.setClickable(false);
//                            light.setFocusable(false);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                }
//        );
//
//
//    }
    private void display() {

//        check = FirebaseDatabase.getInstance().getReference().child("Settings");
//        String th = (String.valueOf(check.child("isAutomatic")));
//
//        if (th == "1") {
//            min.setClickable(true);
//            min.setFocusable(true);
//            max.setClickable(true);
//            max.setFocusable(true);
//            air.setClickable(true);
//            air.setFocusable(true);
//            fan.setClickable(true);
//            fan.setFocusable(true);
//            light.setClickable(true);
//            light.setFocusable(true);
//        }
//        if (th == "0") {
//            min.setClickable(false);
//            min.setFocusable(false);
//            max.setClickable(false);
//            max.setFocusable(false);
//            air.setClickable(false);
//            air.setFocusable(false);
//            fan.setClickable(false);
//            fan.setFocusable(false);
//            light.setClickable(false);
//            light.setFocusable(false);
//        }


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

                                            int min1 = Integer.parseInt(String.valueOf(min.getText()));
                                            int max1 =Integer.parseInt(String.valueOf(max.getText()));
                                            int air1 =Integer.parseInt(String.valueOf(air.getText()));
                                            int fan1 =Integer.parseInt(String.valueOf(fan.getText()));
                                            int light1 =Integer.parseInt(String.valueOf(light.getText()));
                                            int water1 =Integer.parseInt(String.valueOf(water.getText()));
                                            int servo1 =Integer.parseInt(String.valueOf(servo.getText()));
                                            userRef.child("Temperature").child("Min").setValue(min1);
                                            userRef.child("Temperature").child("Max").setValue(max1);
                                            userRef.child("Carbon").child("Min").setValue(air1);
                                            userRef.child("Fan").child("Time").setValue(fan1);
                                            userRef.child("Light").child("Time").setValue(light1);
                                            userRef.child("Water").child("Time").setValue(water1);
                                            userRef.child("Servo").child("Time").setValue(servo1);
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