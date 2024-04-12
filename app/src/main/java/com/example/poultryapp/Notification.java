package com.example.poultryapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Notification extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    MyAdapter adapter;

    private DatePickerDialog datePickerDialogStart,datePickerDialogEnd;
    RecyclerView rv;
    ArrayList<Logs> list;
    ArrayList<String> array,mySort;
    int i=0;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 101;

    EditText searchbar, searchDate,searchtodate;

    ImageView print, back,arrowImage;
    DatabaseReference searchData,searchDateData;
    ToggleButton sort;
    // Convert the string to a Date object
    Date datez = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        list = new ArrayList<>();
        array = new ArrayList<>();

        rv = findViewById(R.id.rc);
        print = findViewById(R.id.printbtn);
        showLogs();
        searchDate = findViewById(R.id.searchfromdate);

        back = findViewById(R.id.backnotif);
        searchbar = findViewById(R.id.searchbar);
        sort = findViewById(R.id.arrowImage);
        mySort = new ArrayList<>();
        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Notification.this, ProfileSettings.class));

                    }
                }
        );

        searchbar.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(!s.toString().isEmpty()){
                            searchData = FirebaseDatabase.getInstance().getReference().child("ActivityLog");
                            array.clear();
                            list.clear();


                            searchData.addValueEventListener(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot snap : snapshot.getChildren()) {
                                                String data = String.valueOf(snap.getValue());
                                                String value[] = data.split("-");
                                                if(value[0].trim().toLowerCase().contains(s.toString().trim().toLowerCase())){
                                                    array.add(data);
                                                    list.add(new Logs(value[1], value[2], value[0]));
                                                    rv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                                    adapter = new MyAdapter(list);
                                                    rv.setAdapter(adapter);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    }
                            );
                        }else{
                            showLogs();
                        }
                    }
                }
        );





        print.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
            if(checkPer()){
                createPdf();
//                Toast.makeText(Notification.this, "Already Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Notification.this, "Was NOt Granted", Toast.LENGTH_SHORT).show();
                requestPermission();
            }
                    }
                }
        );

        initDatePicker();
        searchDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openDateStart();

                    }
                }
        );

        sorter();
    }

    private  void initDatePicker(){
        initDatePickerStart();
        initDatePickerEnd();
    }
    DatabaseReference sorters;
    private void sorter(){

        sort.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        mySort.clear();
                        list.clear();
                        array.clear();
                        sorters = FirebaseDatabase.getInstance().getReference().child("ActivityLog");
                        sorters.addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snap : snapshot.getChildren()) {
                                            String data = String.valueOf(snap.getValue());
                                            Log.d("wwww", String.valueOf(snap.getValue()));
                                            String value[] = data.split("-");
                                            mySort.add(data);


                                        }
                                                                if(isChecked){

                                        Collections.reverse(mySort);
                                for(String datas : mySort){
                                    array.add(datas);
                                    Log.d("Reversed", datas);
                                    String val[] = datas.split("-");
                                    list.add(new Logs(val[1], val[2], val[0]));
                                    rv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                    adapter = new MyAdapter(list);
                                    rv.setAdapter(adapter);
                                }
                        }else{
                                                                    mySort.clear();
                                                                    list.clear();
                                                                    array.clear();
                                                                    showLogs();
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



    private void searchTroughDate(String dates){
             String fromDate = searchDate.getText().toString();
        if(!TextUtils.isEmpty(fromDate)) {

            searchDateData = FirebaseDatabase.getInstance().getReference().child("ActivityLog");
            array.clear();
            list.clear();

            searchDateData.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                String data = String.valueOf(snap.getValue());

                                String date[] = String.valueOf(snap.getValue()).split("-");
                                Log.d("MyDATE", date[1]);
                                try {
                                    if (fromDate.equals(date[1])) {
                                        array.add(data);
                                        list.add(new Logs(date[1], date[2], date[0]));
                                        rv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                                        adapter = new MyAdapter(list);
                                        rv.setAdapter(adapter);
                                    }
                                } catch (Exception e) {

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );
        }else{
            showLogs();
        }


    }

    public Date convertStringToDate(String dateString, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            Date date = formatter.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void initDatePickerStart(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = makeDateString(dayOfMonth,month,year);
                searchDate.setText(date);
                searchTroughDate(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialogStart = new DatePickerDialog(this,style,dateSetListener,year,month,day);

    }

    private void initDatePickerEnd(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = makeDateString(dayOfMonth,month,year);
                searchtodate.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialogEnd = new DatePickerDialog(this,style,dateSetListener,year,month,day);
    }

    private String makeDateString(int day,int month, int year){
        return year+"/"+(month<10?"0"+month:month)+"/"+day;
    }


    public void openDateStart( ){
        datePickerDialogStart.show();
    }
    public void openDateEnd( ){
        datePickerDialogEnd.show();
    }
    DatabaseReference ref;
    public int CODE = 101;
    public void showLogs(){
        array.clear();
        list.clear();

            ref = FirebaseDatabase.getInstance().getReference().child("ActivityLog");
            ref.addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                String data = String.valueOf(snap.getValue());
                                Log.d("wwww", String.valueOf(snap.getValue()));
                                String value[] = data.split("-");
                                array.add(data);
                                list.add(new Logs(value[1], value[2], value[0]));
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


    private void requestPermission(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            try{
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                storageActivityResultLauncher.launch(intent);

            }catch (Exception e){
                Log.d("PERMISSIONS", e.toString());
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package",this.getPackageName(),null);
                intent.setData(uri);
                storageActivityResultLauncher.launch(intent);
            }
        }else{
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            },CODE);
        }
    }

    private ActivityResultLauncher<Intent> storageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                        if(Environment.isExternalStorageManager()){
 createPdf();

                           }else{
                            Toast.makeText(Notification.this, "Denied", Toast.LENGTH_SHORT).show();
                        }
                    }else{

                    }
                }
            }
    );
    public boolean checkPer(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }else{
            int write = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==CODE){
            if(grantResults.length>0){
                boolean write = grantResults[0]==PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1]==PackageManager.PERMISSION_GRANTED;
                if(read && write){
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    DatabaseReference logs;
    int count=0;
    public void createPdf() {
        // Create a new document
        Document document = new Document();

        try {
            // Create a new PdfWriter instance
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + getCurrentDatenospace();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            // Open the document
            document.open();

            // Load the background image
            Drawable drawable = this.getResources().getDrawable(R.drawable.logojollygray);
            Image background = Image.getInstance(drawableToBytes(drawable));

            // Scale the image to 80% of the page size
            float width = document.getPageSize().getWidth() * 0.5f;
            float height = document.getPageSize().getHeight() * 0.5f;
            background.scaleToFit(width, height);

            // Center the image on the page
            float x = (document.getPageSize().getWidth() - background.getScaledWidth()) / 2;
            float y = (document.getPageSize().getHeight() - background.getScaledHeight()) / 2;
            background.setAbsolutePosition(x, y);

            // Set the opacity of the background image
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.2f); // Set opacity value between 0 (transparent) and 1 (opaque)

            // Add the background image with opacity
            PdfContentByte content = writer.getDirectContentUnder();
            content.saveState();
            content.setGState(gs);
            content.addImage(background);

            // Restore the state
            content.restoreState();

            // Create a font for the title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Font titleFont1 = new Font(Font.FontFamily.HELVETICA, 13, Font.ITALIC);
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 11);

            // Create a paragraph with the title
            Paragraph title = new Paragraph("Poultry Monitoring System using Arduino and Android Technology", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20); // Set spacing after the title (in points)

            Paragraph subtitle = null;
            if(searchDate.getText().equals("")){
                subtitle = new Paragraph("Activity Log Report", titleFont1);
            }else{
                subtitle = new Paragraph("Activity Log Report as of " +searchDate.getText().toString(), titleFont1);

            }

            subtitle.setAlignment(Element.ALIGN_CENTER);
            subtitle.setSpacingAfter(30); // Set spacing after the title (in points)

            document.add(title);
            document.add(subtitle);
            //START OF TABLE
            // Create a table with 3 columns
            PdfPTable table = new PdfPTable(4);

            // Create a font for the table header
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            // Add table headers with bold font and center alignment
            PdfPCell headerCell1 = new PdfPCell(new Paragraph("No.", headerFont));
            headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell1);

            PdfPCell headerCell2 = new PdfPCell(new Paragraph("Date", headerFont));
            headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell2);

            PdfPCell headerCell3 = new PdfPCell(new Paragraph("Time", headerFont));
            headerCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell3);

            PdfPCell headerCell4 = new PdfPCell(new Paragraph("Activity", headerFont));
            headerCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell4);


            for (String data1: array) {
                String cell[] = data1.split("-");
                count= count+1;
                table.addCell(String.valueOf(count));
                table.addCell(cell[1]);
                table.addCell(cell[2]);
                table.addCell(cell[0]);
            }


            // Add the table to the document
            document.add(table);


            //FOOTER
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String email = String.valueOf(auth.getCurrentUser().getEmail());

            Paragraph footer = new Paragraph("\t\t\t\t\tDate Printed: "+getCurrentDate()+" "+getCurrentTime()+"          \t \t \t\t  Printed By: "+email,footerFont);
            document.add(footer);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Close the document
            document.close();
        }
        array.clear();
        new ActivityLogs().addLog("printed a activity report");
        Toast.makeText(this, "PDF SAVED SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
    }
    private byte[] drawableToBytes(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    public String getCurrentDate() {
        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

        // Get the current date
        Date currentDate = new Date();

        // Format the current date using the SimpleDateFormat object
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }

    public String getCurrentTime() {
        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Create a SimpleDateFormat object with the desired format
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH);

        // Get the current time
        String currentTime = dateFormat.format(calendar.getTime());

        return currentTime;
    }

    public String getCurrentDatenospace() {
        // Create a SimpleDateFormat object with the desired format
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("Mdyyyy", Locale.ENGLISH);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hhmmssa", Locale.ENGLISH);

        // Get the current date
        Date currentDate = new Date();

        // Format the current date using the SimpleDateFormat object
        String formattedDate = dateFormat.format(currentDate);
        String formattedtime = timeFormat.format(calendar.getTime());

        return "/Poultry"+formattedDate+formattedtime+".pdf";
    }

}