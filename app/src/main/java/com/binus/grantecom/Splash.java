package com.binus.grantecom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    Button loginBtn, regisBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            FirebaseUser mFirebaseUser = mAuth.getCurrentUser();

            if(mFirebaseUser != null) {
                startActivity(new Intent(Splash.this, MainActivity.class));
            }
        } catch (Exception e) {

        }

        loginBtn = findViewById(R.id.login_btn);
        regisBtn = findViewById(R.id.register_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Splash.this, Login.class));
            }
        });

        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Splash.this, Register.class));
            }
        });
    }


}