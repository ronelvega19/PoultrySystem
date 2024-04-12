package com.example.poultryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class changepassword extends AppCompatActivity {
    EditText pass, newPass, confirmPass;
    DatabaseReference checking;
    FirebaseAuth auths;
    FirebaseUser use;
    DatabaseReference user;
    String id;
    Button btn;
            ImageView back;


    boolean isMatch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        pass = findViewById(R.id.passFill);
        newPass = findViewById(R.id.newFill);
        confirmPass = findViewById(R.id.confirm);
        checking = FirebaseDatabase.getInstance().getReference().child("Users");
        auths = FirebaseAuth.getInstance();
        use = auths.getCurrentUser();
        btn = findViewById(R.id.signupBTN);
        back = findViewById(R.id.backBTN);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(changepassword.this,ProfileSettings.class));
            }
        });


passwordf();








    }
    public void passwordf(){
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checking.addValueEventListener(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot snap : snapshot.getChildren()){
                                            String password = String.valueOf(snap.child("password").getValue());
                                            if(password.equals(String.valueOf(pass.getText()))){
                                                if(String.valueOf(confirmPass.getText()).equals(String.valueOf(newPass.getText()))){
                                                    // Update the password
                                                    use.updatePassword(String.valueOf(confirmPass.getText()))
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        // Password updated successfully, show a success message

                                                                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
                                                                        user = FirebaseDatabase.getInstance().getReference().child("Users");
                                                                        user.addListenerForSingleValueEvent(
                                                                                new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                        for(DataSnapshot snap: snapshot.getChildren()){
                                                                                            String users = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                                                                            String passw = String.valueOf(pass.getText());
                                                                                            String Fuser = String.valueOf(snap.child("email").getValue());
                                                                                            String Fpass = String.valueOf(snap.child("password").getValue());

                                                                                            if(users.equals(Fuser) && passw.equals(Fpass)){
                                                                                                id = snap.getKey().toString();
                                                                                                isMatch=true;

                                                                                            }

                                                                                        }

                                                                                        if(isMatch){
                                                                                            userRef.child(id).child("password").setValue(String.valueOf(confirmPass.getText()));
                                                                                            isMatch=false;
                                                                                            new ActivityLogs().addLog("change the account password");
                                                                                            Toast.makeText(getApplicationContext(), "Password updated successfully!", Toast.LENGTH_SHORT).show();
                                                                                        }

                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                    }
                                                                                }
                                                                        );
                                                                    } else {
                                                                        // Password update failed, show an error message
                                                                        Log.d("PassError",task.getException().getMessage());
                                                                        Toast.makeText(getApplicationContext(), "Failed to update password: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                }else{
                                                    Toast.makeText(changepassword.this, "Password must be equal", Toast.LENGTH_SHORT).show();
                                                }
                                            }
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