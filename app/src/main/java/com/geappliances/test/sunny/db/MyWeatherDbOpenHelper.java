package com.geappliances.test.sunny.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @file MyWeatherDbOpenHelper.java
 * @date 08/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class MyWeatherDbOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "myweather.db";
    private static int DATABASE_VERSION = 1;

    //    DB Scheme
    public static String DATABASE_SCHEME_CITY_ID = "CITYID";
    public static String DATABASE_SCHEME_COUNTRY = "COUNTRY";
    public static String DATABASE_SCHEME_CITY = "CITY";
    public static String DATABASE_SCHEME_DATE = "DATE";
    public static String DATABASE_SCHEME_ID = "ID";
    public static String DATABASE_SCHEME_TEMP = "TEMPERTURE";
    public static String DATABASE_SCHEME_TEMP_MIN = "TEMPMIN";
    public static String DATABASE_SCHEME_TEMP_MAX = "TEMPMAX";
    public static String DATABASE_SCHEME_HUMIDITY = "HUMIDITY";
    public static String DATABASE_SCHEME_WINDDIG = "WINDDIG";
    public static String DATABASE_SCHEME_WINDSPEED = "WINDSPEED";
    public static String DATABASE_SCHEME_PRESSURE = "PRESSURE";
    public static String DATABASE_SCHEME_SUNRISE = "SUNRISE";
    public static String DATABASE_SCHEME_SUNSET = "SUNSET";
    public static String DATABASE_SCHEME_SEQ = "CURRENTLOCATION"; // 1: auto 0: added
    public static String DATABASE_SCHEME_TIME_ZONE = "TIMEZONE";

    public MyWeatherDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
                MyWeatherDbHelper.TABLE_NAME + "( " +
                DATABASE_SCHEME_CITY_ID + " TEXT, " +
                DATABASE_SCHEME_COUNTRY + " TEXT, " +
                DATABASE_SCHEME_CITY + " TEXT, " +
                DATABASE_SCHEME_DATE + " LONG DEFAULT 0, " +
                DATABASE_SCHEME_ID + " INTEGER DEFAULT 0, " +
                DATABASE_SCHEME_TEMP + " REAL DEFAULT 0, " +
                DATABASE_SCHEME_TEMP_MIN + " REAL DEFAULT 0, " +
                DATABASE_SCHEME_TEMP_MAX + " REAL DEFAULT 0, " +
                DATABASE_SCHEME_HUMIDITY + " INTEGER DEFAULT 0, " +
                DATABASE_SCHEME_WINDDIG + " INTEGER DEFAULT 0, " +
                DATABASE_SCHEME_WINDSPEED + " REAL DEFAULT 0, " +
                DATABASE_SCHEME_PRESSURE + " INTEGER DEFAULT 0, " +
                DATABASE_SCHEME_SUNRISE + " LONG DEFAULT 0,  " +
                DATABASE_SCHEME_SUNSET + " LONG DEFAULT 0,  " +
                DATABASE_SCHEME_SEQ + " INTEGER DEFAULT 0, " +
                DATABASE_SCHEME_TIME_ZONE + " TEXT, " +
                "PRIMARY KEY (" + DATABASE_SCHEME_CITY_ID + ", " + DATABASE_SCHEME_SEQ + ")" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}