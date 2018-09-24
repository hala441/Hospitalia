package com.example.boody_laptop.hospitalia;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewExercise extends AppCompatActivity {

    int image_id;
    String name;

    TextView title,timer,txtCountDown,txtGetReady;
    ImageView detail_image;

    LinearLayout layout;
    Button btnStart;
    Boolean isRunning=false;
    YogaDB yogaDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);

        yogaDB= new YogaDB(this);
        title=(TextView)findViewById(R.id.title);
        timer=(TextView)findViewById(R.id.timer);
        txtCountDown=(TextView)findViewById(R.id.txtCountDown);
        txtGetReady=(TextView)findViewById(R.id.txtGetReady);
        detail_image=(ImageView)findViewById(R.id.detail_image);

        layout=(LinearLayout)findViewById(R.id.layout_get_ready);

        btnStart=(Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnStart.getText().toString().toLowerCase().equals("start")){
                    showGetReady();
                    btnStart.setText("stop");
                }
                else if(btnStart.getText().toString().toLowerCase().equals("stop"))
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
                    txtCountDown.setText("");
                }
                else {
                    //showRestTime();
                }
            }
        });

        timer.setText("");

        if(getIntent() !=null){
            image_id=getIntent().getIntExtra("image_id",-1);
            name=getIntent().getStringExtra("name");
            detail_image.setImageResource(image_id);
            title.setText(name);
        }
    }

    private void showGetReady() {
        detail_image.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);
        txtCountDown.setVisibility(View.VISIBLE);
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
            detail_image.setVisibility(View.VISIBLE);
            layout.setVisibility(View.INVISIBLE);
            txtCountDown.setVisibility(View.VISIBLE);
            btnStart.setVisibility(View.VISIBLE);
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

    //CountDown
    CountDownTimer exercisesEasyModeCountDown= new CountDownTimer(common.TIME_LIMIT_Easy,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText(""+millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            btnStart.setText("Start");
            finish();
        }
    };
    CountDownTimer exercisesMediumModeCountDown= new CountDownTimer(common.TIME_LIMIT_Medium,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText(""+millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            btnStart.setText("start");
            finish();
        }
    };
    CountDownTimer exercisesHardModeCountDown= new CountDownTimer(common.TIME_LIMIT_Hard,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText(""+millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            btnStart.setText("Start");
        }
    };

}
