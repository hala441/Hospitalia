package com.example.boody_laptop.hospitalia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class YogaDB extends SQLiteAssetHelper{
    private static final String DB_name = "yoga5.db";
    private static final int DB_VER=1;
    int num;

    public YogaDB(Context context) {
        super(context, DB_name, null, DB_VER);
    }
    public int getSettingMode(){
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect= {"Mode"};
        String sqlTable = "Setting";

        qb.setTables(sqlTable);
        Cursor c= qb.query(db,sqlSelect,null,null,null,null,null);
        //c.moveToFirst();
        if(c != null && c.moveToFirst()){
             num = c.getInt(c.getColumnIndex("Mode"));
            c.close();
        }
        return num;
    }

    public void SaveSettingMode (int Value){
        SQLiteDatabase db=getReadableDatabase();
        String query = "UPDATE Setting SET Mode = "+Value;
        db.execSQL(query);
    }

    public List<String> getWorkoutDays(){
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String[] sqlSelect= {"Day"};
        String sqlTable="WorkoutDays";

        qb.setTables(sqlTable);
        Cursor c=qb.query(db,sqlSelect,null,null,null,null,null);

        List<String> result= new ArrayList<>();
        if(c.moveToFirst()){
            do{
                result.add(c.getString(c.getColumnIndex("Day")));
            }while (c.moveToNext());
        }
        return result;
    }

    public void SaveDay (String Value){
        SQLiteDatabase db=getReadableDatabase();
        String query = String.format("INSERT INTO WorkoutDays(Day) VALUES('%s'); ",Value);
        db.execSQL(query);
    }
}
