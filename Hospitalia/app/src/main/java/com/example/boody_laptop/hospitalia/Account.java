package com.example.boody_laptop.hospitalia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account2);
    }

    public void buclick(View view){
        Intent myintent = new Intent(this,Home2Activity.class);
    }
    public void buclick2(View view){

    }
    public void buclick3(View view){

    }
    public void buclick4(View view){

    }
    public void buclick5(View view){

    }
}
