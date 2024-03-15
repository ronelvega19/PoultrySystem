package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {

    MyAdapter adapter;

    RecyclerView rv;
    ArrayList<Logs> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        list = new ArrayList<>();

        rv = findViewById(R.id.rc);
        showLogs();
    }
    DatabaseReference ref;

    public void showLogs(){
        ref = FirebaseDatabase.getInstance().getReference().child("ActivityLog");
        ref.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap: snapshot.getChildren()){
                            String data = String.valueOf(snap.getValue());
                            Log.d("wwww", String.valueOf(snap.getValue()));
                            String value[] = data.split("-");
                            list.add(new Logs(value[1],value[2],value[0]));
                            rv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            adapter = new MyAdapter(list);
                            rv.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }







}