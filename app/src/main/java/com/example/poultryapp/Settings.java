package com.example.poultryapp;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CompoundButton;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.app.TimePickerDialog;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;


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

import java.util.Locale;

public class Settings extends AppCompatActivity {
    TimePickerDialog timePickerDialog;
    EditText min, max, air, fan, light, water, servo;
    TextView a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17;
    Button feed, save;
    Switch switchh;
    int hour, minute;
    int h;
    DatabaseReference check;

    String dataSelected, dataSelected1;

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
        feed = findViewById(R.id.timeButton);

        a1 = findViewById(R.id.textView);
        a2 = findViewById(R.id.textView2);
        a3 = findViewById(R.id.textView11);
        a4 = findViewById(R.id.textView22);
        a5 = findViewById(R.id.textView23);
        a6 = findViewById(R.id.textView111);
        a7 = findViewById(R.id.textView1111);
        a8 = findViewById(R.id.textView113);
        a9 = findViewById(R.id.textView1133);
        a10 = findViewById(R.id.textView11331);
        a11 = findViewById(R.id.textView101);
        a12 = findViewById(R.id.textView1011);
        a13 = findViewById(R.id.textView22115);
        a14 = findViewById(R.id.textView2211);
        a15 = findViewById(R.id.textView22113);
        a16 = findViewById(R.id.textView221131);
        a17 = findViewById(R.id.textView221131s);

        switchh = findViewById(R.id.switchhh);


        switchh.setOnCheckedChangeListener(

                new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        DatabaseReference userRef2 = FirebaseDatabase.getInstance().getReference().child("Settings");
                        check = FirebaseDatabase.getInstance().getReference().child("Settings");
                        check.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snap : snapshot.getChildren()) {
                                            if (!isChecked) {
                                                userRef2.child("isAutomatic").setValue("1");
                                                a1.setVisibility(View.GONE);
                                                a2.setVisibility(View.GONE);
                                                a3.setVisibility(View.GONE);
                                                a4.setVisibility(View.GONE);
                                                a5.setVisibility(View.GONE);
                                                a6.setVisibility(View.GONE);
                                                a7.setVisibility(View.GONE);
                                                a8.setVisibility(View.GONE);
                                                a9.setVisibility(View.GONE);
                                                a10.setVisibility(View.GONE);
                                                a11.setVisibility(View.GONE);
                                                a12.setVisibility(View.GONE);
                                                a13.setVisibility(View.GONE);
                                                a14.setVisibility(View.GONE);
                                                a15.setVisibility(View.GONE);
                                                a16.setVisibility(View.GONE);
                                                a17.setVisibility(View.GONE);
                                                min.setVisibility(View.GONE);
                                                max.setVisibility(View.GONE);
                                                air.setVisibility(View.GONE);
                                                fan.setVisibility(View.GONE);
                                                light.setVisibility(View.GONE);
                                                water.setVisibility(View.GONE);
                                                servo.setVisibility(View.GONE);
                                                feed.setVisibility(View.GONE);
                                                save.setVisibility(View.GONE);
                                                feed.setVisibility(View.GONE);
                                            }
                                            if (isChecked) {
                                                userRef2.child("isAutomatic").setValue("0");
                                                a1.setVisibility(View.VISIBLE);
                                                a2.setVisibility(View.VISIBLE);
                                                a3.setVisibility(View.VISIBLE);
                                                a4.setVisibility(View.VISIBLE);
                                                a5.setVisibility(View.VISIBLE);
                                                a6.setVisibility(View.VISIBLE);
                                                a7.setVisibility(View.VISIBLE);
                                                a8.setVisibility(View.VISIBLE);
                                                a9.setVisibility(View.VISIBLE);
                                                a10.setVisibility(View.VISIBLE);
                                                a11.setVisibility(View.VISIBLE);
                                                a12.setVisibility(View.VISIBLE);
                                                a13.setVisibility(View.VISIBLE);
                                                a14.setVisibility(View.VISIBLE);
                                                a15.setVisibility(View.VISIBLE);
                                                a16.setVisibility(View.VISIBLE);
                                                a17.setVisibility(View.VISIBLE);
                                                min.setVisibility(View.VISIBLE);
                                                max.setVisibility(View.VISIBLE);
                                                air.setVisibility(View.VISIBLE);
                                                fan.setVisibility(View.VISIBLE);
                                                light.setVisibility(View.VISIBLE);
                                                water.setVisibility(View.VISIBLE);
                                                servo.setVisibility(View.VISIBLE);
                                                feed.setVisibility(View.VISIBLE);
                                                save.setVisibility(View.VISIBLE);
                                                feed.setVisibility(View.VISIBLE);
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

        display();
        initTime();
    }


    private void initTime(){
        TimePickerDialog.OnTimeSetListener timer = new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hours, int minutes){

                String stat = hours<12?"AM":"PM";
                dataSelected = (hours<10 ? "0"+String.valueOf(hours):String.valueOf(hours))+":"+(minutes<10?"0"+String.valueOf(minutes):String.valueOf(minutes))+":00 "+stat;
                dataSelected1 = (hours<10 ? "0"+String.valueOf(hours):String.valueOf(hours))+":"+(minutes<10?"0"+String.valueOf(minutes):String.valueOf(minutes))+":00 ";

                if (hours>12) {
                    h = hours - 12;
                    dataSelected = (hours<10 ? "0"+String.valueOf(h):String.valueOf(h))+":"+(minutes<10?"0"+String.valueOf(minutes):String.valueOf(minutes))+":00 "+stat;
                    dataSelected1 = (hours<10 ? "0"+String.valueOf(h):String.valueOf(h))+":"+(minutes<10?"0"+String.valueOf(minutes):String.valueOf(minutes))+":00 ";
                }

                feed.setText(dataSelected);
                //Toast.makeText(getContext(),"Time Selected: "+dataSelected,Toast.LENGTH_SHORT).show();
            }
        };
        timePickerDialog = new TimePickerDialog(this,timer,0,0,false);
    }

    public void popTimePicker(View view)
    {
        timePickerDialog.show();
    }


    DatabaseReference settings, time;

    DatabaseReference rd;
    FirebaseAuth userData;

    private void display() {

        check = FirebaseDatabase.getInstance().getReference().child("Settings");
        String th = (String.valueOf(check.child("isAutomatic")));

        if (th == "1") {
            a1.setVisibility(View.GONE);
            a2.setVisibility(View.GONE);
            a3.setVisibility(View.GONE);
            a4.setVisibility(View.GONE);
            a5.setVisibility(View.GONE);
            a6.setVisibility(View.GONE);
            a7.setVisibility(View.GONE);
            a8.setVisibility(View.GONE);
            a9.setVisibility(View.GONE);
            a10.setVisibility(View.GONE);
            a11.setVisibility(View.GONE);
            a12.setVisibility(View.GONE);
            a13.setVisibility(View.GONE);
            a14.setVisibility(View.GONE);
            a15.setVisibility(View.GONE);
            a16.setVisibility(View.GONE);
            a17.setVisibility(View.GONE);
            min.setVisibility(View.GONE);
            max.setVisibility(View.GONE);
            air.setVisibility(View.GONE);
            fan.setVisibility(View.GONE);
            light.setVisibility(View.GONE);
            water.setVisibility(View.GONE);
            servo.setVisibility(View.GONE);
            feed.setVisibility(View.GONE);
            save.setVisibility(View.GONE);
            feed.setVisibility(View.GONE);
        }
        if (th == "0") {
            a1.setVisibility(View.VISIBLE);
            a2.setVisibility(View.VISIBLE);
            a3.setVisibility(View.VISIBLE);
            a4.setVisibility(View.VISIBLE);
            a5.setVisibility(View.VISIBLE);
            a6.setVisibility(View.VISIBLE);
            a7.setVisibility(View.VISIBLE);
            a8.setVisibility(View.VISIBLE);
            a9.setVisibility(View.VISIBLE);
            a10.setVisibility(View.VISIBLE);
            a11.setVisibility(View.VISIBLE);
            a12.setVisibility(View.VISIBLE);
            a13.setVisibility(View.VISIBLE);
            a14.setVisibility(View.VISIBLE);
            a15.setVisibility(View.VISIBLE);
            a16.setVisibility(View.VISIBLE);
            a17.setVisibility(View.VISIBLE);
            min.setVisibility(View.VISIBLE);
            max.setVisibility(View.VISIBLE);
            air.setVisibility(View.VISIBLE);
            fan.setVisibility(View.VISIBLE);
            light.setVisibility(View.VISIBLE);
            water.setVisibility(View.VISIBLE);
            servo.setVisibility(View.VISIBLE);
            feed.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
            feed.setVisibility(View.VISIBLE);
        }


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



                        DatabaseReference userRef1 = FirebaseDatabase.getInstance().getReference().child("Time");
                        time = FirebaseDatabase.getInstance().getReference().child("Time");
                        time.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snap : snapshot.getChildren()) {
                                            userRef1.child("DefaultTime").setValue(String.valueOf(dataSelected1));
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