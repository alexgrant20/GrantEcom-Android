package com.binus.grantecom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.Calendar;

public class ChangeProfileFragment extends Fragment  {

    EditText profileUsername, profileName, profileDateOfBirth, profilePhoneNumber;
    String userid, name, username, phoneNumber, dateOfBirth, userId;
    Button profileSubmitBtn;

    public void ChangeProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        username = bundle.getString("username");
        name = getArguments().getString("name");
        dateOfBirth = getArguments().getString("dateOfBirth");
        phoneNumber = getArguments().getString("phoneNumber");
        userId = getArguments().getString("userId");

        View rootView = inflater.inflate(R.layout.fragment_change_profile, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        profileUsername = getView().findViewById(R.id.profile_username);
        profileName = getView().findViewById(R.id.profile_name);
        profileDateOfBirth = getView().findViewById(R.id.profile_date_of_birth);
        profilePhoneNumber = getView().findViewById(R.id.profile_phone_number);
        profileSubmitBtn = getView().findViewById(R.id.submit_btn);

        profileUsername.setText(username);
        profileName.setText(name);
        profileDateOfBirth.setText(dateOfBirth);
        profilePhoneNumber.setText(phoneNumber);
        profileDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                profileDateOfBirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        profileSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child(userId);

                username = profileUsername.getText().toString();
                name = profileName.getText().toString();
                phoneNumber = profilePhoneNumber.getText().toString();
                dateOfBirth = profileDateOfBirth.getText().toString();

                UserHelperClass userHelperClass = new UserHelperClass(name, username, phoneNumber, dateOfBirth);
                reference.setValue(userHelperClass);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new AccountFragment()).commit();
            }
        });
    }
}