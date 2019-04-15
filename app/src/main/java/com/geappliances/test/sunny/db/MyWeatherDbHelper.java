package com.geappliances.test.sunny.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.geappliances.test.sunny.common.Dlog;
import com.geappliances.test.sunny.data.WeatherData;

import java.util.ArrayList;

/**
 * @file MyWeatherDbHelper.java
 * @date 08/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class MyWeatherDbHelper implements WeatherDbHelper {

    public static final String TABLE_NAME = "myweather";
    private final SQLiteDatabase db;

    public MyWeatherDbHelper(Context context) {
        MyWeatherDbOpenHelper databaseOpenHelper = new MyWeatherDbOpenHelper(context);
        db = databaseOpenHelper.getWritableDatabase();
    }

    @Override
    public WeatherData find(int cityId) {
        Cursor cursor = null;
        WeatherData model = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +
                    MyWeatherDbOpenHelper.DATABASE_SCHEME_CITY_ID + " = " + cityId, null);
            if (cursor != null && cursor.moveToFirst()) {
                model = new WeatherData();
                model.setCityId(cityId);

                model.setCountry(cursor.getString(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_COUNTRY)));
                model.setCity(cursor.getString(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_CITY)));
                model.setDate(cursor.getLong(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_DATE)));

                model.setId(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_ID)));
                model.setTemp(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_TEMP)));
                model.setTempMax(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_TEMP_MAX)));
                model.setTempMin(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_TEMP_MIN)));
                model.setHumidity(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_HUMIDITY)));
                model.setWindDig(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_WINDDIG)));
                model.setWindSpeed(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_WINDSPEED)));
                model.setPressure(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_PRESSURE)));


                model.setSunrise(cursor.getLong(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_SUNRISE)));
                model.setSunset(cursor.getLong(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_SUNSET)));
                model.setTimeZone(cursor.getString(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_TIME_ZONE)));
                Dlog.d("db", "findWeather9" + model.getCity());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (model != null) {
            Dlog.d("db", "findWeather9" + model.getCity());
        }

        return model;
    }

    @Override
    public void insert(WeatherData model) {

        try {
            Dlog.d("db", "insert" + model.getCity());
            ContentValues values = new ContentValues();

            Long date = System.currentTimeMillis();
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_CITY_ID, model.getCityId());

            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_COUNTRY, model.getCountry());
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_CITY, model.getCity());
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_DATE, date);
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_ID, model.getId());
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_TEMP, model.getTemp());
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_TEMP_MAX, model.getTempMax());
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_TEMP_MIN, model.getTempMin());
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_HUMIDITY, model.getHumidity());
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_WINDDIG, model.getWindDig());
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_WINDSPEED, model.getWindSpeed());
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_PRESSURE, model.getPressure());

            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_SUNRISE, model.getSunrise()*1000);
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_SUNSET, model.getSunset()*1000);
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_SEQ, model.getSeq());
            values.put(MyWeatherDbOpenHelper.DATABASE_SCHEME_TIME_ZONE, model.getTimeZone());

            db.insert(TABLE_NAME, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeExist(int id) {
        if (find(id) != null) {
            remove(id);
        }
    }


    @Override
    public ArrayList<WeatherData> fetchAllContent() {
        ArrayList<WeatherData> list = new ArrayList<>();
        WeatherData model;
        Cursor cursor = null;
        try {

            cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + MyWeatherDbOpenHelper.DATABASE_SCHEME_SEQ + " ASC", null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {

                    model = new WeatherData();
                    Dlog.d("db", "불러오기 " + cursor.getString(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_CITY)));

                    model.setCityId(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_CITY_ID)));

                    model.setCountry(cursor.getString(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_COUNTRY)));
                    model.setCity(cursor.getString(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_CITY)));
                    model.setDate(cursor.getLong(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_DATE)));

                    model.setId(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_ID)));
                    model.setTemp(cursor.getDouble(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_TEMP)));
                    model.setTempMax(cursor.getDouble(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_TEMP_MAX)));
                    model.setTempMin(cursor.getDouble(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_TEMP_MIN)));
                    model.setHumidity(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_HUMIDITY)));
                    model.setWindDig(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_WINDDIG)));
                    model.setWindSpeed(cursor.getDouble(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_WINDSPEED)));
                    model.setPressure(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_PRESSURE)));


                    model.setSunrise(cursor.getLong(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_SUNRISE)));
                    model.setSunset(cursor.getLong(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_SUNSET)));
                    model.setSeq(cursor.getInt(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_SEQ)));
                    model.setTimeZone(cursor.getString(cursor.getColumnIndex(MyWeatherDbOpenHelper.DATABASE_SCHEME_TIME_ZONE)));
                    list.add(model);
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return list;
    }


    @Override
    public void update(WeatherData model) {
        removeExist(model.getCityId());
        insert(model);


    }

    @Override
    public void updateCurrentItem(WeatherData model) {
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + MyWeatherDbOpenHelper.DATABASE_SCHEME_SEQ + " = '0'");
        insert(model);
    }

    @Override
    public void remove(int id) {
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + MyWeatherDbOpenHelper.DATABASE_SCHEME_CITY_ID + "= '" + id + "' AND " + MyWeatherDbOpenHelper.DATABASE_SCHEME_SEQ + " != '0'");
    }

    @Override
    public int countAllContents() {
        int count = (int) db.compileStatement( "select count(*) from "+ TABLE_NAME ).simpleQueryForLong();
        Dlog.d("db", "count " + count);

        return count;
    }


}
