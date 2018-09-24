package com.example.boody_laptop.hospitalia;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

public class SettingsYoga extends AppCompatActivity {

    Button btnSave;
    RadioGroup rdiGroup;
    RadioButton rdiEasy,rdiMedium,rdiHard;
    ToggleButton switchAlarm;
    TimePicker timePicker;
    YogaDB yogaDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_yoga);

        //initView
        btnSave=(Button)findViewById(R.id.btnSave);
        rdiGroup=(RadioGroup)findViewById(R.id.rdiGroup);
        rdiEasy=(RadioButton)findViewById(R.id.rdiEasy);
        rdiMedium=(RadioButton)findViewById(R.id.rdiMedium);
        rdiHard=(RadioButton)findViewById(R.id.rdiHard);
        switchAlarm=(ToggleButton)findViewById(R.id.switchAlarm);
        timePicker=(TimePicker)findViewById(R.id.timePicker);
        yogaDB=new YogaDB(this);
        
        int mode = yogaDB.getSettingMode();
        setRadioButton(mode);

        //Event
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWorkoutMode();
                saveAlarm(switchAlarm.isChecked());
                finish();
            }
        });
    }

    private void saveAlarm(boolean checked) {
        if(checked){
            AlarmManager manager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent;
            PendingIntent pendingIntent;

            intent=new Intent(SettingsYoga.this, AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

            //Set time to Alarm
            Calendar calendar = Calendar.getInstance();
            Date today= Calendar.getInstance().getTime();
            calendar.set(today.getYear(),today.getMonth(),today.getDay(),
                               timePicker.getCurrentHour(),timePicker.getCurrentMinute());
            manager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()
                    ,AlarmManager.INTERVAL_DAY,pendingIntent);
            Log.d("DEBUG","Alarm will wake at : "+
                    timePicker.getCurrentHour()+":"+ timePicker.getCurrentMinute());
            }
        else {
            //cancel Alarm
            Intent intent=new Intent(SettingsYoga.this,AlarmNotificationReceiver.class);
            PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent,0);
            AlarmManager manager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            manager.cancel(pendingIntent);
        }
    }

    private void saveWorkoutMode(){
        int selectedID=rdiGroup.getCheckedRadioButtonId();
        if(selectedID == rdiEasy.getId()){
            yogaDB.SaveSettingMode(0);
        }
        else if(selectedID == rdiMedium.getId()){
            yogaDB.SaveSettingMode(1);
        }
        else if(selectedID == rdiHard.getId()){
            yogaDB.SaveSettingMode(2);
        }
    }
    public void setRadioButton(int mode) {
        if(mode == 0){
            rdiGroup.check(R.id.rdiEasy);
        }
        else if(mode == 1){
            rdiGroup.check(R.id.rdiMedium);
        }
        else if(mode == 2){
            rdiGroup.check(R.id.rdiHard);
        }
    }
}
