package com.example.boody_laptop.hospitalia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FeedBackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Feedback");
    }
    public void buclick(View view){
        EditText Feedback= findViewById(R.id.feedback);
        EditText username= findViewById(R.id.user_name);
        Intent feedback= new Intent(this,Home2Activity.class);
        Bundle b=new Bundle();
        b.putString("username",username.getText().toString());
        b.putString("Feedback",Feedback.getText().toString());
        feedback.putExtras(b);
        if(!Feedback.getText().toString().isEmpty()){
            Toast.makeText(FeedBackActivity.this,"Thank you",Toast.LENGTH_SHORT).show();
            startActivity(feedback);
            finish();
        }
        else{
            Toast.makeText(FeedBackActivity.this,
                    "Please fill the empty field",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
