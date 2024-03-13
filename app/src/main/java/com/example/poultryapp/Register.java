package com.example.poultryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    TextView reguser,regpass,regconfirmpass;
    Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reguser = findViewById(R.id.regUserTXT);
        regpass = findViewById(R.id.regPasswordTXT);
        regconfirmpass = findViewById(R.id.regRetypePasswordTXT);
        signin = findViewById(R.id.regBtn);
        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(reguser.getText().toString()) && TextUtils.isEmpty(regpass.getText().toString()) && TextUtils.isEmpty(regconfirmpass.getText().toString())){
                            Toast.makeText(getApplicationContext(), "Please enter all in the field", Toast.LENGTH_SHORT).show();
                        }else{

                            if(regpass.getText().toString().equals(regconfirmpass.getText().toString())){
                                new RegistrationData().setEmail(reguser.getText().toString());
                                new RegistrationData().setPassword(regconfirmpass.getText().toString());
                                startActivity(new Intent(Register.this,SMSVerification.class));
                            }else{
                                Toast.makeText(Register.this, "The password must be equal", Toast.LENGTH_SHORT).show();
                            }
                            //APPROVE


                        }
                    }
                }
        );

    }

}