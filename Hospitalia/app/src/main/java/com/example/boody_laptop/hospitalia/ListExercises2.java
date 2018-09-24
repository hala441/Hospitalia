package com.example.boody_laptop.hospitalia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListExercises2 extends AppCompatActivity {

    List<Exercises> exercisesList=new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_exercises2);

        initData();
        recyclerView=(RecyclerView)findViewById(R.id.list_ex);
        adapter=new RecyclerViewAdapter(exercisesList,getBaseContext());
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {
        exercisesList.add(new Exercises(R.drawable.bent_arm_pullover,"ArnoldPress"));
        exercisesList.add(new Exercises(R.drawable.back_extensions_on_swiss_ball,"Back extensions on swiss ball"));
        exercisesList.add(new Exercises(R.drawable.barbell_front_raises,"Barbell Front Raises"));
        exercisesList.add(new Exercises(R.drawable.barbell_shrugs,"Barbell Shrugs"));
        exercisesList.add(new Exercises(R.drawable.barbell_upright_rows,"Barbell upright rows"));
        exercisesList.add(new Exercises(R.drawable.bench_press_narrow_grip,"Bench press (narrow grip)"));
        //exercisesList.add(new Exercises(R.drawable.upward_bow,"Bench press (one arm)"));
        exercisesList.add(new Exercises(R.drawable.benchpress,"Bench press"));
        //exercisesList.add(new Exercises(R.drawable.warrior_pose,"Bench press (close grip)"));
        exercisesList.add(new Exercises(R.drawable.bent_arm_pullover,"Bent arm pullover"));
        exercisesList.add(new Exercises(R.drawable.bent_knee_hip_raise,"Bent knee hip raise"));
        exercisesList.add(new Exercises(R.drawable.bicep_hammer_curl,"Bicep hammer curl"));
        exercisesList.add(new Exercises(R.drawable.bicep_curl_alternate,"Bicep-curl (alternate)"));
        //exercisesList.add(new Exercises(R.drawable.downward_facing_dog,"Biceps concentration curl(on ball, one arm)"));
        //exercisesList.add(new Exercises(R.drawable.boat_pose,"Biceps curl (prone, incline)"));
        exercisesList.add(new Exercises(R.drawable.bridge,"Bridge"));
        exercisesList.add(new Exercises(R.drawable.butterflies,"Butterflies (incline)"));
        exercisesList.add(new Exercises(R.drawable.butterflies_with_a_twist,"Butterflies with a twist (incline)"));
       // exercisesList.add(new Exercises(R.drawable.crescent_lunge,"Butterfly (one arm, flat bench)"));
        exercisesList.add(new Exercises(R.drawable.concentration_triceps_kneeling,"concentration triceps exetension (kneeling)"));

      //  exercisesList.add(new Exercises(R.drawable.warrior_pose,"Curl"));
     //   exercisesList.add(new Exercises(R.drawable.bow_pose,"Curl (Barbell)"));
        exercisesList.add(new Exercises(R.drawable.culr_incline_alternate,"Curl (incline, alternate)"));
        exercisesList.add(new Exercises(R.drawable.dips,"Dips"));
        exercisesList.add(new Exercises(R.drawable.dips_with_dip_stands,"Dips (with dip stands)"));
        exercisesList.add(new Exercises(R.drawable.dips_with_stands2,"Dips (with dip stands 2)"));

        exercisesList.add(new Exercises(R.drawable.dumbell_press,"Dumbbell Press (incline)"));
        exercisesList.add(new Exercises(R.drawable.high_cable_curl,"High Cable curl"));
        exercisesList.add(new Exercises(R.drawable.hyperextensions,"Hyperextensions)"));
        exercisesList.add(new Exercises(R.drawable.leglift_at_wall,"Leglift (at wall)"));
        exercisesList.add(new Exercises(R.drawable.leg_press,"Leg press"));
        exercisesList.add(new Exercises(R.drawable.leg_raises,"Leg raises"));
        exercisesList.add(new Exercises(R.drawable.low_extension,"Low triceps exetension"));
        exercisesList.add(new Exercises(R.drawable.lunge_barbell,"Lunges (barbell)"));
        exercisesList.add(new Exercises(R.drawable.lunges2,"Lunges (dumbbell)"));
        exercisesList.add(new Exercises(R.drawable.lying_cable_curl,"Lying bicep cable curl"));
        exercisesList.add(new Exercises(R.drawable.pushdown_incline,"Pushdown (incline)"));

        exercisesList.add(new Exercises(R.drawable.row_pullup_bar,"Row (with pull-up bar)"));
        exercisesList.add(new Exercises(R.drawable.squat_one_leg,"Squat(one leg)"));
        exercisesList.add(new Exercises(R.drawable.squat_overhead,"Squat (overhead)"));
        exercisesList.add(new Exercises(R.drawable.squat,"Squat"));
        exercisesList.add(new Exercises(R.drawable.squat2,"Squat2"));
    }
}
