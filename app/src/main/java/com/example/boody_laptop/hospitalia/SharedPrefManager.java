package com.example.boody_laptop.hospitalia;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by j on 13/04/2018.
 */

public class SharedPrefManager {

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_NAME = "fullname";
    private static final String KEY_PASSWORD = "userpassword";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_EMAIL = "useremail";
    private static final String KEY_USER_TELEPHONE = "usertelephone";
    private static final String KEY_USER_CITY = "usercity";
    public static final String KEY_USER_ID = "ID";
    private static final String KEY_USER_MOBILE = "usermobile";
    private static final String KEY_USER_CATEGORY = "usercategory";
    private static final String KEY_USER_GENDER = "usergender";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }
    public static boolean userLogin(String name, String username, String password, String city, String email, String mobile, String category, String telephone, String gender){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_USERNAME,username);
        editor.putString(KEY_USER_EMAIL,email);
        editor.putString(KEY_USER_CITY,city);
        //    editor.putString(KEY_USER_ID,id);
        editor.putString(KEY_USER_TELEPHONE,telephone);
        editor.putString(KEY_USER_CATEGORY,category);
        editor.putString(KEY_USER_MOBILE,mobile);
        editor.putString(KEY_USER_GENDER,gender);
        editor.putString(KEY_PASSWORD,password);
        // editor.putString(KEY_USER_MY_DOCTOR,mydoctor);
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

    public static boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getname(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null);
    }

    public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getTelephone(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_TELEPHONE, null);
    }

    public String getGender(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_GENDER, null);
    }

    public String getMobile(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_MOBILE, null);
    }

    public String getCategory(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_CATEGORY, null);
    }

    public String getEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getPassword() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public String getCity() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_CITY, null);
    }

    public static HashMap<String, String> getUserDetail(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME , Context.MODE_PRIVATE);
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_NAME, sharedPreferences.getString(KEY_NAME, null));
        user.put(KEY_USER_EMAIL, sharedPreferences.getString(KEY_USER_EMAIL, null));
        user.put(KEY_USER_ID, sharedPreferences.getString(KEY_USER_ID, null));
        return user;
    }


}

