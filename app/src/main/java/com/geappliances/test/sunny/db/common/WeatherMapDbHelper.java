package com.geappliances.test.sunny.db.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.geappliances.test.sunny.common.Dlog;

import java.util.ArrayList;

import static com.geappliances.test.sunny.db.common.CityItem.DATABASE_SCHEME_CITY_ID;
import static com.geappliances.test.sunny.db.common.CityItem.DATABASE_SCHEME_CITY_NAME;
import static com.geappliances.test.sunny.db.common.CityItem.DATABASE_SCHEME_COUNTRY_NAME;
import static com.geappliances.test.sunny.db.common.CityItem.DATABASE_SCHEME_LATITUDE;
import static com.geappliances.test.sunny.db.common.CityItem.DATABASE_SCHEME_LONGITUDE;

/**
 * @file WeatherMapDbHelper.java
 * @date 07/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class WeatherMapDbHelper implements CoppiedDbHelper {

    public static final String DATABASE_TABLE_NAME = "weathermap";
    public static final String CITY_LIST_TABLE = "owm_city_list";
    private SQLiteDatabase db;


    public WeatherMapDbHelper(Context context) {
        WeathermapDbCopyHelper databaseOpenHelper = new WeathermapDbCopyHelper(context);
        db = databaseOpenHelper.getReadableDatabase();

    }

    @Override
    public WeatherMapItem findWeather(int id) {

        Cursor cursor = null;
        WeatherMapItem item = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE_NAME + " WHERE " + WeatherMapItem.DATABASE_SCHEME_ID + " = " + id, null);
            if (cursor != null && cursor.moveToFirst()) {
                item = new WeatherMapItem(id, cursor.getString(cursor.getColumnIndex(WeatherMapItem.DATABASE_SCHEME_MEANING)), cursor.getString(cursor.getColumnIndex(WeatherMapItem.DATABASE_SCHEME_ICON)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (item != null) {
            Dlog.d("db", "findWeather0" + item.getMeaning());
        }

        return item;
    }

    @Override
    public ArrayList<CityItem> findCityList(String cityName, int limit, int offset) {
        Dlog.d("db", "findWeather2" + cityName);
        ArrayList<CityItem> cityItems = new ArrayList<>();
        Cursor cursor = null;
        CityItem item = null;
        try {
            cursor = db.rawQuery("SELECT DISTINCT * FROM " + CITY_LIST_TABLE + " WHERE " + DATABASE_SCHEME_CITY_NAME + " LIKE '%" + cityName +"%' OR " + DATABASE_SCHEME_COUNTRY_NAME + " LIKE '%" + cityName + "%' LIMIT " + limit + " OFFSET " + offset, null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
//                    Dlog.d("테스트!", "요기2");
                    item = new CityItem(cursor.getString(cursor.getColumnIndex(CityItem.DATABASE_SCHEME_CITY_NAME)), cursor.getInt(cursor.getColumnIndex(DATABASE_SCHEME_CITY_ID)), cursor.getString(cursor.getColumnIndex(DATABASE_SCHEME_COUNTRY_NAME)), cursor.getFloat(cursor.getColumnIndex(DATABASE_SCHEME_LONGITUDE)), cursor.getFloat(cursor.getColumnIndex(DATABASE_SCHEME_LATITUDE)));
                    cityItems.add(item);
//                    Dlog.d("db", "findWeather1" + item.cityName);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (item != null) {
            Dlog.d("db", "findWeather   re" + item.cityName);
        }

        return cityItems;

    }

    @Override
    public CityItem findCityItem(int cityId) {
        Dlog.d("db", "findWeather2" + cityId);
        Cursor cursor = null;
        CityItem item = null;
        try {
            cursor = db.rawQuery("SELECT DISTINCT * FROM " + CITY_LIST_TABLE  + " WHERE '" + DATABASE_SCHEME_CITY_ID +" = " + cityId + "", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
//                    Dlog.d("테스트!", "요기2");
                    item = new CityItem(cursor.getString(cursor.getColumnIndex(CityItem.DATABASE_SCHEME_CITY_NAME)), cursor.getInt(cursor.getColumnIndex(DATABASE_SCHEME_CITY_ID)), cursor.getString(cursor.getColumnIndex(DATABASE_SCHEME_COUNTRY_NAME)), cursor.getFloat(cursor.getColumnIndex(DATABASE_SCHEME_LONGITUDE)), cursor.getFloat(cursor.getColumnIndex(DATABASE_SCHEME_LATITUDE)));
//                    Dlog.d("db", "findWeather1" + item.cityName);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (item != null) {
            Dlog.d("db", "findWeather   re" + item.cityName);
        }

        return item;

    }



}
