package com.binus.grantecom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button changeProfileBtn;
    UserHelperClass userHelperClass;

    TextView nameAccount, usernameAccount, phoneNumberAccount, dateOfBirthAccount;
    String userid, name, username, phoneNumber, dateOfBirth;

    public void AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        try{
            FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            userid = mFirebaseUser.getUid();

        }catch (Exception e) {

        }

        firebaseDatabase = firebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("users").child(userid);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userHelperClass = snapshot.getValue(UserHelperClass.class);

                name = userHelperClass.getName();
                dateOfBirth = userHelperClass.getDateOfBirth();
                phoneNumber = userHelperClass.getPhoneNumber();
                username = userHelperClass.getUsername();

                nameAccount.setText(name);
                dateOfBirthAccount.setText(dateOfBirth);
                phoneNumberAccount.setText(phoneNumber);
                usernameAccount.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameAccount = getView().findViewById(R.id.profile_name);
        usernameAccount = getView().findViewById(R.id.profile_username);
        phoneNumberAccount = getView().findViewById(R.id.profile_phone_number);
        dateOfBirthAccount = getView().findViewById(R.id.profile_date_of_birth);
        changeProfileBtn = getView().findViewById(R.id.change_profile);

        changeProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                bundle.putString("name", name);
                bundle.putString("phoneNumber", phoneNumber);
                bundle.putString("dateOfBirth", dateOfBirth);
                bundle.putString("userId", userid);

                ChangeProfileFragment changeProfileFragment = new ChangeProfileFragment();
                changeProfileFragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, changeProfileFragment).commit();
            }
        });
    }


}
