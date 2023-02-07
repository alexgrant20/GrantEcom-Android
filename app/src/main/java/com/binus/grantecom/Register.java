package com.binus.grantecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Register extends AppCompatActivity {
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    EditText regName, regUsername, regEmail, regPhoneNumber, regDateOfBirth, regPassword;
    Button regBtn;
    ImageView registerIcon;
    TextView loginText;
    private FirebaseAuth mAuth;
    int isUserCreated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            FirebaseUser mFirebaseUser = mAuth.getCurrentUser();

            if(mFirebaseUser != null) {
                startActivity(new Intent(Register.this, MainActivity.class));
            }
        } catch (Exception e) {

        }

        mAuth = FirebaseAuth.getInstance();

        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNumber = findViewById(R.id.reg_phone_number);
        regDateOfBirth = findViewById(R.id.reg_date_of_birth);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        loginText = findViewById(R.id.login_text);

        registerIcon = findViewById(R.id.reg_icon);

        regDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Register.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                regDateOfBirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                String name = regName.getText().toString();
                String username = regUsername.getText().toString();
                String email = regEmail.getText().toString();
                String phoneNumber = regPhoneNumber.getText().toString();
                String dateOfBirth = regDateOfBirth.getText().toString();
                String password = regPassword.getText().toString();

                if (email.length() == 0 || password.length() == 0 ||
                        name.length() == 0 || username.length() == 0 ||
                        dateOfBirth.length() == 0 || phoneNumber.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Data can't be empty!",
                            Toast.LENGTH_SHORT).show();

                    return;
                }else if (!email.matches("[a-zA-Z0-9.-]+@[a-z]+\\.+[a-z]+")){
                    Toast.makeText(getApplicationContext(),
                            "Please follow common email pattern!",
                            Toast.LENGTH_SHORT).show();

                    return;
                } else if(password.length() < 6) {
                    Toast.makeText(getApplicationContext(),
                            "Password need to have 6 character or more",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                mAuth
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    isUserCreated = 1;
                                }
                            }
                        });

                if(isUserCreated == 0){
                    Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                            .show();

                    return;
                }

                FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                UserHelperClass userHelperClass = new UserHelperClass(name, username, phoneNumber, dateOfBirth);

                reference.child(mFirebaseUser.getUid()).setValue(userHelperClass);

                Toast.makeText(getApplicationContext(),
                                "Registration successful!",
                                Toast.LENGTH_LONG)
                        .show();

                Intent myIntent = new Intent(Register.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }



}