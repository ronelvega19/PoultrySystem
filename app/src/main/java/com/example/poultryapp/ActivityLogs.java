package com.example.poultryapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityLogs {
    DatabaseReference actlog;
    FirebaseAuth email;
    public void addLog(String data){
        email = FirebaseAuth.getInstance();
        String username = String.valueOf(email.getCurrentUser().getEmail());
        // for activity log data
        actlog = FirebaseDatabase.getInstance().getReference().child("ActivityLog");

        String currentDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        String actLOG = "User " + username + " "+data+". -" + currentDate + "-" + currentTime;

        actlog.push().setValue(actLOG);

    }
}
