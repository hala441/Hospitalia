package com.example.boody_laptop.hospitalia;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by j on 14/04/2018.
 */

public class SharedPrefManager2 {
    private static SharedPrefManager2 mInstance;
    private  Context mCtx;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_NAME = "fullname";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USER_PASSWORD = "userpassword";
    private static final String KEY_USER_Age = "userage";
    private static final String KEY_USER_gender = "usergender";
    private static final String KEY_USER_contact = "usercontact";
    private static final String KEY_USER_weight = "userweight";
    private static final String KEY_USER_MEDICAL_HISTORY = "usermmedicalhistory";
    private static final String KEY_USER_DEVICEID = "userdeviceid";
    private static final String KEY_USER_MY_DOCTOR = "usermydoctor";
    private static final String KEY_USER_COMMENT = "mydrcommebt";
    private static final String KEY_USER_HELPNUMBER = "myhelpnumber";





    private SharedPrefManager2(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager2 getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager2(context);
        }
        return mInstance;
    }
    public boolean userLogin( String name ,String username,String password,  String gender, String email, String contact, String medicalhistory,  String age  , String weight, String mydoctor,  String deviceid,  String comment,  String help ){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USER_Age,age);
        editor.putString(KEY_USER_gender,gender);
        editor.putString(KEY_USER_weight,weight);
        editor.putString(KEY_USER_PASSWORD,password);
        editor.putString(KEY_USER_DEVICEID,deviceid);
        editor.putString(KEY_USER_MY_DOCTOR,mydoctor);
        editor.putString(KEY_USER_MEDICAL_HISTORY,medicalhistory);
        editor.putString(KEY_USER_contact,contact);
        editor.putString(KEY_USER_COMMENT,comment);
        editor.putString(KEY_USER_HELPNUMBER,help);

        editor.apply();
        return true;

    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME, null)!= null){
            return  true;
        }
        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }
    public String gethelpnumber(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_HELPNUMBER, null);
    }
    public String getContact(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_contact, null);
    }

    public String getMedicalHistory(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_MEDICAL_HISTORY, null);
    }

    public String getcomment(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_COMMENT, null);
    }

    public String getName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null);
    }

    public String getEmail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }
    public String getAge(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_Age, null);
    }

    public String getGender(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_gender, null);
    }

    public String getDoctor(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_MY_DOCTOR, null);
    }

    public String getWeight(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_weight, null);
    }

    public String getPassword(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_PASSWORD, null);
    }

    public String getDeviceid(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_DEVICEID, null);
    }

}


