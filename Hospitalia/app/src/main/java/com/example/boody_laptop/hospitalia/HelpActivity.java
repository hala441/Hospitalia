package com.example.boody_laptop.hospitalia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void buclick(View view){
        EditText problem= findViewById(R.id.help);
        EditText username= findViewById(R.id.user_name);
        Intent help = new Intent(this,Home2Activity.class);
        Bundle b=new Bundle();
        b.putString("username",username.getText().toString());
        b.putString("Problem",problem.getText().toString());
        help.putExtras(b);
        Toast.makeText(HelpActivity.this,"Thank you.We will solve it as far as we can.",Toast.LENGTH_SHORT).show();
        startActivity(help);
        finish();
    }
}
