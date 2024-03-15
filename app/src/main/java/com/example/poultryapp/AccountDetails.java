package com.example.poultryapp;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountDetails extends AppCompatActivity {

    TextView email,fname,lname,pass;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        fname = findViewById(R.id.firstNameTXT);
        lname = findViewById(R.id.lastNameTXT);
        pass = findViewById(R.id.passwordTXT);
        email = findViewById(R.id.emailTXT);
        done = findViewById(R.id.doneBTN);

        display();

    }
    DatabaseReference user;
    String id;
    boolean isMatch=false;
    DatabaseReference rd;
    FirebaseAuth userData;
    private void display(){
        email.setEnabled(false);
        pass.setEnabled(false);

        userData = FirebaseAuth.getInstance();
        rd = FirebaseDatabase.getInstance().getReference().child("Users");
        String emails = String.valueOf(userData.getCurrentUser().getEmail());

        rd.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(String.valueOf(snap.child("email").getValue()).equals(emails)){
                                fname.setText(String.valueOf(snap.child("first_name").getValue()));
                                lname.setText(String.valueOf(snap.child("last_name").getValue()));
                                email.setText(String.valueOf(snap.child("email").getValue()));
                                pass.setText(String.valueOf(snap.child("password").getValue()));

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );


        done.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
                        user = FirebaseDatabase.getInstance().getReference().child("Users");
                        user.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot snap: snapshot.getChildren()){
                                            String users = String.valueOf(email.getText());
                                            String passw = String.valueOf(pass.getText());
                                            String Fuser = String.valueOf(snap.child("email").getValue());
                                            String Fpass = String.valueOf(snap.child("password").getValue());

                                            if(users.equals(Fuser) && passw.equals(Fpass)){
                                                 id = snap.getKey().toString();
                                                isMatch=true;

                                            }

                                        }

                                        if(isMatch){
                                            userRef.child(id).child("first_name").setValue(String.valueOf(fname.getText()));
                                            userRef.child(id).child("last_name").setValue(String.valueOf(lname.getText()));
                                            SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("fName",String.valueOf(fname.getText()));
                                            editor.putString("lName",String.valueOf(lname.getText()));
                                            editor.apply();
                                            isMatch=false;
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