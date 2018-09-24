package com.example.boody_laptop.hospitalia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class FittnessTracker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fittness_tracker);

        CardView yoga = (CardView) findViewById(R.id.yogacardId);
        CardView workout = (CardView) findViewById(R.id.workoutcardId);

        final Intent intent=new Intent(this,MainYoga.class);
        final Intent intent2=new Intent(this,MainWorkout.class);

        yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });
    }
}
