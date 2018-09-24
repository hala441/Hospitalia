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

public class DailyTraining extends AppCompatActivity {

    Button btnStart;
    ImageView ex_image;
    TextView txtGetReady,txtCountDown,txtTimer,ex_name;
    ProgressBar progressBar;
    LinearLayout layoutGetReady;
    int ex_id=0,limit_time=0;
    List<Exercises> list=new ArrayList<>();
    YogaDB yogaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_training);

        yogaDB =new YogaDB(this);

        initData();

        if(yogaDB.getSettingMode() == 0){
            limit_time=common.TIME_LIMIT_Easy;
        }
        else if(yogaDB.getSettingMode() == 1){
            limit_time=common.TIME_LIMIT_Medium;
        }
        else if(yogaDB.getSettingMode() == 2){
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
                    if(yogaDB.getSettingMode() == 0){
                        exercisesEasyModeCountDown.cancel();
                    }
                    else if(yogaDB.getSettingMode() == 1){
                        exercisesMediumModeCountDown.cancel();
                    }
                    else if(yogaDB.getSettingMode() == 2){
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
                    if(yogaDB.getSettingMode() == 0){
                        exercisesEasyModeCountDown.cancel();
                    }
                    else if(yogaDB.getSettingMode() == 1){
                        exercisesMediumModeCountDown.cancel();
                    }
                    else if(yogaDB.getSettingMode() == 2){
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
                if(yogaDB.getSettingMode() == 0){
                    exercisesEasyModeCountDown.start();
                }
                else if(yogaDB.getSettingMode() == 1){
                    exercisesMediumModeCountDown.start();
                }
                else if(yogaDB.getSettingMode() == 2){
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
        yogaDB.SaveDay(""+ Calendar.getInstance().getTimeInMillis());
    }

    private void initData() {
        list.add(new Exercises(R.drawable.easy_pose,"Easy Pose"));
        list.add(new Exercises(R.drawable.cobra_pose,"Cobra Pose"));
        list.add(new Exercises(R.drawable.downward_facing_dog,"Downward Facing Dog"));
        list.add(new Exercises(R.drawable.boat_pose,"Boat Pose"));
        list.add(new Exercises(R.drawable.half_pigeon,"Half Pigeon"));
        list.add(new Exercises(R.drawable.low_lunge,"Low Lunge"));
        list.add(new Exercises(R.drawable.upward_bow,"Upward Pose"));
        list.add(new Exercises(R.drawable.crescent_lunge,"Crescent Lunge"));
        list.add(new Exercises(R.drawable.warrior_pose,"Warrior Pose"));
        list.add(new Exercises(R.drawable.bow_pose,"Bow Pose"));
        list.add(new Exercises(R.drawable.warrior_pose_2,"Warrior Pose 2"));
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
}
