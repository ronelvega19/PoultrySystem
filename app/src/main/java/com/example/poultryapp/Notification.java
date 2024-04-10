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
                        new ActivityLogs().addLog("logs in");
                    }
                }
        );






        print.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        per();
//                        permission();
                    }
                }
        );
    }
    DatabaseReference ref;

    public void savepdf(){

        PdfDocument pdfDoc = null;
        DatabaseReference data;
        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            String cdate1 = sdf1.format(new Date());
            pdfDoc = new PdfDocument(new PdfWriter(path+cdate1));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Document doc = new Document(pdfDoc);
        Paragraph t = new Paragraph("Poultry Management System").setTextAlignment(TextAlignment.CENTER).setFontSize(16);
        doc.add(t);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String cdate = sdf.format(new Date());
        Paragraph dates = new Paragraph("Generated on:"+cdate).setTextAlignment(TextAlignment.RIGHT).setFontSize(12).setMarginTop(10);
        doc.add(dates);
        data = FirebaseDatabase.getInstance().getReference().child("ActivityLog");
        Table table = new Table(3);
        table.addHeaderCell("No");
        table.addHeaderCell("Logs");
        table.addHeaderCell("Date");

        data.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap: snapshot.getChildren()){
                            String data = String.valueOf(snap.getValue());
                            String value[] = data.split("-");
                            i= i+1;
                            table.addCell(new Cell().add(new Paragraph(String.valueOf(i))));
                            table.addCell(new Cell().add(new Paragraph(value[0])));
                            table.addCell(new Cell().add(new Paragraph(value[1]+" - "+value[2])));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

        doc.add(table);
        doc.close();
        Toast.makeText(this, "Save Pdf Successfully!", Toast.LENGTH_SHORT).show();

    }
    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 100;
public  void per(){

// Check if the WRITE_EXTERNAL_STORAGE permission is granted
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
        // Permission already granted, perform necessary actions
        performActions();
    } else {
        // Request the WRITE_EXTERNAL_STORAGE permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
    }
}

    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, perform necessary actions
                performActions();
            } else {
                // Permission denied, handle accordingly
                handlePermissionDenied();
            }
        }
    }

    // Perform necessary actions when the permission is granted
    private void performActions() {
        // TODO: Implement your code here
        savepdf();
    }

    // Handle the scenario when the permission is denied
    private void handlePermissionDenied() {
        // TODO: Implement your code here
    }

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