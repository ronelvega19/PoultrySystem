package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Notification extends AppCompatActivity {

    MyAdapter adapter;


    RecyclerView rv;
    ArrayList<Logs> list;
    int i=0;
    ImageView print, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        list = new ArrayList<>();

        rv = findViewById(R.id.rc);
        print = findViewById(R.id.printbtn);
        showLogs();

        back = findViewById(R.id.backnotif);

        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Notification.this, ProfileSettings.class));

                    }
                }
        );






        print.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        permission();
                    }
                }
        );
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



//    private void permission(){
//      if(Build.VERSION.SDK_INT>=Build.VERSION_CODES)
//    }



}