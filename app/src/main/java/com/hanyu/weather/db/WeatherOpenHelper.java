package com.hanyu.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dell on 2016/7/21.
 */
public class WeatherOpenHelper extends SQLiteOpenHelper {

    /*
    * 省
    * */
    public static final String CREATE_PROVINCE = "create table Province("
            + "id integer primary key autoincrement,"
            + "province_name text,"
            + "province_code text)";
    /*
    * 市
    * */
    public static final String CREATE_CITY = "create table City("
            + "id integer primary autoincrement, "
            + "city_name text,"
            + "city_code,"
            + "province_id integer) ";
    /*
    * 县
    * */
    public static final String CREATE_COUNTY = "create table county("
            + "id integer primary autoincrement,"
            + "county_name text,"
            + "county_code,"
            + "city_id integer)";





    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,name,factory,version);
    }


    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }






}
