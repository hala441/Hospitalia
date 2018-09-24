package com.example.boody_laptop.hospitalia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class PatientProfileActivity extends AppCompatActivity {

    private TextView textViewUsername, textViewPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewPassword = (TextView) findViewById(R.id.textViewPassword);

        textViewUsername.setText(SharedPrefManager2.getInstance(this).getUsername());

    }
}
