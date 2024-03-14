package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class    SMSVerification extends AppCompatActivity {

    private boolean isValidPhoneNumber(String phoneNumber) {
        return Patterns.PHONE.matcher(phoneNumber).matches();
    }



    EditText otpInput;
    Button nextBtn;

    String num="+63";

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

                         num = "+63"+otpInput.getText().toString();

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

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(SMSVerification.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                Log.d("OTPERROR",e.getMessage().toString());
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                Log.d("SUCCESSOTP",s);
                                Toast.makeText(SMSVerification.this, "OTP SENT SUCCESSFULLY", Toast.LENGTH_SHORT).show();

                                signIn(number,s);


                            }





                        }
                ).build();

        PhoneAuthProvider.verifyPhoneNumber(options);




    }


public void signIn(String nums, String ID){

  Intent intent = new Intent(SMSVerification.this,confirm_sms.class);
  intent.putExtra("number",nums);
  intent.putExtra("token",ID);
    SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();



    editor.putString("number",nums);
    editor.apply();
    intent.putExtra("username",getIntent().getStringExtra("username"));
    intent.putExtra("password",getIntent().getStringExtra("password"));

  startActivity(intent);
}

}