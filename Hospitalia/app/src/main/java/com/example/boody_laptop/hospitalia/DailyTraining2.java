package com.example.boody_laptop.hospitalia;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DailyTraining2 extends AppCompatActivity {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady,txtCountDown,txtTimer,ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;
    int ex_id=0,limit_time=0;
    List<Exercises> list=new ArrayList<>();
    WorkoutDB workoutDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_training);

        workoutDB =new WorkoutDB(this);

        initData();

        if(workoutDB.getSettingMode() == 0){
            limit_time=common.TIME_LIMIT_Easy;
        }
        else if(workoutDB.getSettingMode() == 1){
            limit_time=common.TIME_LIMIT_Medium;
        }
        else if(workoutDB.getSettingMode() == 2){
            limit_time=common.TIME_LIMIT_Hard;
        }
        btnStart=(Button)findViewById(R.id.btnStart);
        ex_image=(ImageView)findViewById(R.id.detail_image);
        txtCountDown=(TextView)findViewById(R.id.txtCountDown);
        txtGetReady=(TextView)findViewById(R.id.txtGetReady);
        txtTimer=(TextView)findViewById(R.id.timer);
        ex_name=(TextView)findViewById(R.id.title);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        layoutGetReady=(LinearLayout)findViewById(R.id.layout_get_ready);

        //Set Data
        progressBar.setMax(list.size());
        setExerciseInformation(ex_id);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnStart.getText().toString().toLowerCase().equals("start")){
                    showGetReady();
                    btnStart.setText("done");
                }
                else if(btnStart.getText().toString().toLowerCase().equals("done"))
                {
                    if(workoutDB.getSettingMode() == 0){
                        exercisesEasyModeCountDown.cancel();
                    }
                    else if(workoutDB.getSettingMode() == 1){
                        exercisesMediumModeCountDown.cancel();
                    }
                    else if(workoutDB.getSettingMode() == 2){
                        exercisesHardModeCountDown.cancel();
                    }
                    restTimeCountDown.cancel();

                    if(ex_id <list.size()){
                        showRestTime();
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");
                    }
                    else
                        showFinished();
                }
                else {
                    showRestTime();
                    if(ex_id <list.size()){
                        progressBar.setProgress(ex_id);
                    }
                    else
                        showFinished();
                }
            }
        });
    }

    private void showRestTime() {
        ex_image.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);
        btnStart.setText("Skip");
        restTimeCountDown.start();
        txtGetReady.setText("REST TIME");
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnStart.getText().toString().toLowerCase().equals("skip")){
                    if(ex_id < list.size()){
                        setExerciseInformation(ex_id);
                        btnStart.setText("start");
                    }
                    else
                        showFinished();
                }
                else if(btnStart.getText().toString().toLowerCase().equals("done"))
                {
                    if(workoutDB.getSettingMode() == 0){
                        exercisesEasyModeCountDown.cancel();
                    }
                    else if(workoutDB.getSettingMode() == 1){
                        exercisesMediumModeCountDown.cancel();
                    }
                    else if(workoutDB.getSettingMode() == 2){
                        exercisesHardModeCountDown.cancel();
                    }
                    restTimeCountDown.cancel();

                    if(ex_id <list.size()){
                        showRestTime();
                        ex_id++;
                        progressBar.setProgress(ex_id);
                        txtTimer.setText("");
                    }
                    else
                        showFinished();
                }
                else{
                    ///////////////////////////
                    if(ex_id <list.size()){
                        progressBar.setProgress(ex_id);
                    }
                    else
                        showFinished();
                    showGetReady();
                    btnStart.setText("done");
                }
            }
        });
    }

    private void showGetReady() {
        ex_image.setVisibility(View.INVISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.INVISIBLE);

        txtGetReady.setText("GET READY");
        new CountDownTimer(6000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                txtCountDown.setText(""+(millisUntilFinished-1000)/1000);
            }
            @Override
            public void onFinish() {
                showExercises();
            }
        }.start();
    }

    private void showExercises() {
        if(ex_id< list.size()){
            ex_image.setVisibility(View.VISIBLE);
            layoutGetReady.setVisibility(View.INVISIBLE);
            txtTimer.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);

            if(btnStart.getText().toString().toLowerCase().equals("done")){
                if(workoutDB.getSettingMode() == 0){
                    exercisesEasyModeCountDown.start();
                }
                else if(workoutDB.getSettingMode() == 1){
                    exercisesMediumModeCountDown.start();
                }
                else if(workoutDB.getSettingMode() == 2){
                    exercisesHardModeCountDown.start();
                }
            }

            //Set Data
            ex_image.setImageResource(list.get(ex_id).getImage_id());
            ex_name.setText(list.get(ex_id).getName());
        }
        else {
            showFinished();
        }
    }
    //CountDown
    CountDownTimer exercisesEasyModeCountDown= new CountDownTimer(common.TIME_LIMIT_Easy,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText(""+millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            if(ex_id <list.size()-1){
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");
                setExerciseInformation(ex_id);
                btnStart.setText("Start");
            }
            else {
                showFinished();
            }
        }
    };
    CountDownTimer exercisesMediumModeCountDown= new CountDownTimer(common.TIME_LIMIT_Medium,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText(""+millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            if(ex_id <list.size()-1){
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");
                setExerciseInformation(ex_id);
                btnStart.setText("start");
            }
            else {
                showFinished();
            }
        }
    };
    CountDownTimer exercisesHardModeCountDown= new CountDownTimer(common.TIME_LIMIT_Hard,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtTimer.setText(""+millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            if(ex_id <list.size()-1){
                ex_id++;
                progressBar.setProgress(ex_id);
                txtTimer.setText("");
                setExerciseInformation(ex_id);
                btnStart.setText("start");
            }
            else {
                showFinished();
            }
        }
    };
    CountDownTimer restTimeCountDown= new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            txtCountDown.setText(""+millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            if(ex_id < list.size()){
                setExerciseInformation(ex_id);
                showExercises();
            }
            else
                showFinished();
        }
    };

    private void showFinished() {
        ex_image.setVisibility(View.INVISIBLE);
        layoutGetReady.setVisibility(View.VISIBLE);
        btnStart.setVisibility(View.INVISIBLE);
        txtTimer.setVisibility(View.INVISIBLE);

        txtGetReady.setText("FINISHED !!");
        txtCountDown.setText("Congratulations ! \nYou're done with today exercises.");
        //txtCountDown.setTextSize(20);
        //Save Workout done to DB
        workoutDB.SaveDay(""+ Calendar.getInstance().getTimeInMillis());
    }

    public void setExerciseInformation(int id) {
        ex_image.setImageResource(list.get(id).getImage_id());
        ex_name.setText(list.get(id).getName());
        //btnStart.setText("start");
        ex_image.setVisibility(View.VISIBLE);
        layoutGetReady.setVisibility(View.INVISIBLE);
        btnStart.setVisibility(View.VISIBLE);
        txtTimer.setVisibility(View.VISIBLE);
    }

    private void initData() {
        list.add(new Exercises(R.drawable.bent_arm_pullover,"ArnoldPress"));
        list.add(new Exercises(R.drawable.back_extensions_on_swiss_ball,"Back extensions on swiss ball"));
        list.add(new Exercises(R.drawable.barbell_front_raises,"Barbell Front Raises"));
        list.add(new Exercises(R.drawable.barbell_shrugs,"Barbell Shrugs"));
        list.add(new Exercises(R.drawable.barbell_upright_rows,"Barbell upright rows"));
        list.add(new Exercises(R.drawable.bench_press_narrow_grip,"Bench press (narrow grip)"));
        //exercisesList.add(new Exercises(R.drawable.upward_bow,"Bench press (one arm)"));
        list.add(new Exercises(R.drawable.benchpress,"Bench press"));
        //exercisesList.add(new Exercises(R.drawable.warrior_pose,"Bench press (close grip)"));
        list.add(new Exercises(R.drawable.bent_arm_pullover,"Bent arm pullover"));
        list.add(new Exercises(R.drawable.bent_knee_hip_raise,"Bent knee hip raise"));
        list.add(new Exercises(R.drawable.bicep_hammer_curl,"Bicep hammer curl"));
        list.add(new Exercises(R.drawable.bicep_curl_alternate,"Bicep-curl (alternate)"));
        //exercisesList.add(new Exercises(R.drawable.downward_facing_dog,"Biceps concentration curl(on ball, one arm)"));
        //exercisesList.add(new Exercises(R.drawable.boat_pose,"Biceps curl (prone, incline)"));
        list.add(new Exercises(R.drawable.bridge,"Bridge"));
        list.add(new Exercises(R.drawable.butterflies,"Butterflies (incline)"));
        list.add(new Exercises(R.drawable.butterflies_with_a_twist,"Butterflies with a twist (incline)"));
        // exercisesList.add(new Exercises(R.drawable.crescent_lunge,"Butterfly (one arm, flat bench)"));
        list.add(new Exercises(R.drawable.concentration_triceps_kneeling,"concentration triceps exetension (kneeling)"));
        //  exercisesList.add(new Exercises(R.drawable.warrior_pose,"Curl"));
        //   exercisesList.add(new Exercises(R.drawable.bow_pose,"Curl (Barbell)"));
        list.add(new Exercises(R.drawable.culr_incline_alternate,"Curl (incline, alternate)"));
        list.add(new Exercises(R.drawable.dips,"Dips"));
        list.add(new Exercises(R.drawable.dips_with_dip_stands,"Dips (with dip stands)"));
        list.add(new Exercises(R.drawable.dips_with_stands2,"Dips (with dip stands 2)"));
        list.add(new Exercises(R.drawable.dumbell_press,"Dumbbell Press (incline)"));
        list.add(new Exercises(R.drawable.high_cable_curl,"High Cable curl"));
        list.add(new Exercises(R.drawable.hyperextensions,"Hyperextensions)"));
        list.add(new Exercises(R.drawable.leglift_at_wall,"Leglift (at wall)"));
        list.add(new Exercises(R.drawable.leg_press,"Leg press"));
        list.add(new Exercises(R.drawable.leg_raises,"Leg raises"));
        list.add(new Exercises(R.drawable.low_extension,"Low triceps exetension"));
        list.add(new Exercises(R.drawable.lunge_barbell,"Lunges (barbell)"));
        list.add(new Exercises(R.drawable.lunges2,"Lunges (dumbbell)"));
        list.add(new Exercises(R.drawable.lying_cable_curl,"Lying bicep cable curl"));
        list.add(new Exercises(R.drawable.pushdown_incline,"Pushdown (incline)"));
        list.add(new Exercises(R.drawable.row_pullup_bar,"Row (with pull-up bar)"));
        list.add(new Exercises(R.drawable.squat_one_leg,"Squat(one leg)"));
        list.add(new Exercises(R.drawable.squat_overhead,"Squat (overhead)"));
        list.add(new Exercises(R.drawable.squat,"Squat"));
        list.add(new Exercises(R.drawable.squat2,"Squat2"));
    }

}
