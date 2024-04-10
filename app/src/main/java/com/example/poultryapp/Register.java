package com.example.poultryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    TextView reguser,regpass,regconfirmpass,fName,lName, signs, tt;
    CheckBox cb;
    static boolean isCheck = false;
    Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reguser = findViewById(R.id.regUserTXT);
        regpass = findViewById(R.id.regPasswordTXT);
        regconfirmpass = findViewById(R.id.regRetypePasswordTXT);
        signin = findViewById(R.id.regBtn);

        cb = findViewById(R.id.termsandconditionscheck);
        tt = findViewById(R.id.signn);
        cb.setChecked(false);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb.setChecked(true);
                }
            }
        });


        tt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Register.this, Terms_and_Conditions.class);
                        startActivity(intent);
                    }
                }
        );


            cb.setChecked(isCheck);






        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(TextUtils.isEmpty(reguser.getText().toString()) && TextUtils.isEmpty(regpass.getText().toString()) && TextUtils.isEmpty(regconfirmpass.getText().toString())){
                            Toast.makeText(getApplicationContext(), "Please enter all in the field", Toast.LENGTH_SHORT).show();
                        }else{
                            //ACTIVATE TERMS AND CONDITIONS

                            if(regpass.getText().toString().equals(regconfirmpass.getText().toString())){
                                SharedPreferences sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();


                                Intent intent = new Intent(Register.this,EmailVerification.class);
                                String username = reguser.getText().toString();
                                String pass = regconfirmpass.getText().toString();
                                String fNames = "";
                                String lNames = "";
                                editor.putString("username",username);
                                editor.putString("password",pass);
                                editor.putString("fName",fNames);
                                editor.putString("lName",lNames);
                                editor.apply();
                                intent.putExtra("username",username);
                                intent.putExtra("password",pass);
                                startActivity(intent);
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