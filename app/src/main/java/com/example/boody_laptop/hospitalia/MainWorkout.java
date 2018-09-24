package com.example.boody_laptop.hospitalia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainWorkout extends AppCompatActivity {

    Button btnExercises,btnSettings,btnCalender;
    ImageView btnTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_workout);
        btnExercises=(Button)findViewById(R.id.btnExercises);
        btnSettings=(Button)findViewById(R.id.btnSettings);
        btnCalender=(Button)findViewById(R.id.btnCalender);
        btnTraining=(ImageView) findViewById(R.id.btnTraining);
        btnCalender=(Button) findViewById(R.id.btnCalender);

        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainWorkout.this,DailyTraining2.class);
                startActivity(intent);
            }
        });

        btnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainWorkout.this,ListExercises2.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainWorkout.this,SettingsWorkout.class);
                startActivity(intent);
            }
        });

        btnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainWorkout.this,Calender2.class);
                startActivity(intent);
            }
        });
    }
}
