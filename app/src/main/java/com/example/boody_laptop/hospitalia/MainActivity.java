package com.example.boody_laptop.hospitalia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }
        else if(SharedPrefManager2.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, Home2Activity.class));
            return;
        }
    }

    public void buclick(View view){
        Intent myintent = new Intent(this,LoginActivity.class);
        Bundle b=new Bundle();
        myintent.putExtras(b);
        startActivity(myintent);
    }
    public void buclick2(View view){
        Intent myintent = new Intent(this,Login2Activity.class);
        Bundle b=new Bundle();
        myintent.putExtras(b);
        startActivity(myintent);
        finish();
    }

}
