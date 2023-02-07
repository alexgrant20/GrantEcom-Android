package com.binus.grantecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextView registerText;
    EditText loginEmail, loginPassword;
    Button loginBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        try {
            FirebaseUser mFirebaseUser = mAuth.getCurrentUser();

            if(mFirebaseUser != null) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        } catch (Exception e) {

        }

        registerText = findViewById(R.id.register_text);
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);

        loginBtn = findViewById(R.id.login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser();
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private void authenticateUser() {

        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Data can't be empty!",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                                    "Login successful!!",
                                                    Toast.LENGTH_LONG)
                                            .show();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                }

                                else {
                                    Toast.makeText(getApplicationContext(),
                                                    "Login failed!!",
                                                    Toast.LENGTH_LONG)
                                            .show();
                                    return;
                                }
                            }
                        });

    }


}