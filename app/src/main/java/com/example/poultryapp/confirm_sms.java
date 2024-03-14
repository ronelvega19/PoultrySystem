package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class confirm_sms extends AppCompatActivity {
    String number, token;
    Button verify;
    EditText code;
    DatabaseReference userAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sms);
        number = getIntent().getStringExtra("number");
        token = getIntent().getStringExtra("token");
        code = findViewById(R.id.numberCode);
        verify = findViewById(R.id.confirm);
        verified();
    }
    public void verified(){
        verify.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(code.getText())){

                        }else{
                            PhoneAuthCredential phone = PhoneAuthProvider.getCredential(
                                    token,
                                    code.getText().toString()
                            );
                            FirebaseAuth.getInstance().signInWithCredential(phone)
                                    .addOnCompleteListener(
                                            new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    Toast.makeText(confirm_sms.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                                                    String user = getIntent().getStringExtra("username");
                                                    String pass = getIntent().getStringExtra("password");
                                                    SharedPreferences datas = getSharedPreferences("myPref", Context.MODE_PRIVATE);

                                                    RegistrationData data = new RegistrationData(datas.getString("username","")
                                                            ,datas.getString("password",""),
                                                            datas.getString("number",""),
                                                            datas.getString("fName",""),
                                                            datas.getString("lName",""));
                                                    userAccount = FirebaseDatabase.getInstance().getReference().child("Users");
                                                    userAccount.push().setValue(data);
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            startActivity(new Intent(confirm_sms.this, verified.class));
                                                        }
                                                    },1000);

                                                }
                                            }
                                    );
                        }
                    }
                }
        );
    }
}