package com.example.boody_laptop.hospitalia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListExercises extends AppCompatActivity {
    List<Exercises> exercisesList=new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises);
        
        initData();
        recyclerView=(RecyclerView)findViewById(R.id.list_ex);
        adapter=new RecyclerViewAdapter(exercisesList,getBaseContext());
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        exercisesList.add(new Exercises(R.drawable.easy_pose,"Easy Pose"));
        exercisesList.add(new Exercises(R.drawable.cobra_pose,"Cobra Pose"));
        exercisesList.add(new Exercises(R.drawable.downward_facing_dog,"Downward Facing Dog"));
        exercisesList.add(new Exercises(R.drawable.boat_pose,"Boat Pose"));
        exercisesList.add(new Exercises(R.drawable.half_pigeon,"Half Pigeon"));
        exercisesList.add(new Exercises(R.drawable.low_lunge,"Low Lunge"));
        exercisesList.add(new Exercises(R.drawable.upward_bow,"Upward Pose"));
        exercisesList.add(new Exercises(R.drawable.crescent_lunge,"Crescent Lunge"));
        exercisesList.add(new Exercises(R.drawable.warrior_pose,"Warrior Pose"));
        exercisesList.add(new Exercises(R.drawable.bow_pose,"Bow Pose"));
        exercisesList.add(new Exercises(R.drawable.warrior_pose_2,"Warrior Pose 2"));
    }
}
