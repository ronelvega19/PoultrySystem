package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import android.util.Patterns;

public class SMSVerification extends AppCompatActivity {

    private boolean isValidPhoneNumber(String phoneNumber) {
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }


    String phoneNumber;
    Long timeoutSeconds = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken  resendingToken;

    EditText otpInput;
    Button nextBtn;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    PhoneAuthProvider.ForceResendingToken frt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsverification);

        otpInput = findViewById(R.id.numberInput);
    nextBtn = findViewById(R.id.otpBTN);
        nextBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String numF = "+63";
                        String num = numF+otpInput.getText().toString();

                        sendOtp(num);
                    }
                }
        );


    }
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public void sendOtp(String number){
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(number).
                setTimeout(60L,TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override

                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(SMSVerification.this, "OTP SENT", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(SMSVerification.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                Log.d("OTPERROR",e.getMessage().toString());
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);





                            }





                        }
                ).build();

        PhoneAuthProvider.verifyPhoneNumber(options);



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = otpInput.getText().toString().trim();
                if (isValidPhoneNumber(num)) {
                    sendOtp(num);
                } else {
                    Toast.makeText(SMSVerification.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }




}