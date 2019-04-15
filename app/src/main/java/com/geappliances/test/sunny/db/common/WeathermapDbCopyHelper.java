package com.geappliances.test.sunny.db.common;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.geappliances.test.sunny.common.Dlog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @file WeathermapDbCopyHelper.java
 * @date 07/02/2019
 * @brief citytable : https://github.com/manifestinteractive/openweathermap-cities
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class WeathermapDbCopyHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weathermap.db";
    private static String DATABASE_PATH;
    private SQLiteDatabase db;
    public static final int DATABASE_VERSION = 1;
    private Context context;


    public WeathermapDbCopyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
        Dlog.d("db", "파일 위치 " + DATABASE_PATH);

        createDataBase();
    }


    private void createDataBase() {
        this.getReadableDatabase();
        this.close();
        copyDataBase();
    }


    //Check database already exist or not
    private boolean checkDataBase() {
        File db = new File(DATABASE_PATH);
        if (!db.exists()) {
            if (!new File(db.getParent()).exists()) {
                new File(db.getParent()).mkdirs();
            }
            return false;
        }
        return true;
    }

    //Copies your database from your local assets-folder to the just created empty database in the system folder
    private void copyDataBase() {
        Dlog.d("db","파일 생성" );

        if(checkDataBase()){
            if (getTabledata()>2){
                return;
            }
        }
        Dlog.d("db","파일 생성 시도" + DATABASE_PATH);

        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);
            String outFileName = DATABASE_PATH;
            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            File file = new File(DATABASE_PATH);
            if(file.exists()){
                Dlog.d("db","파일 생성 완료" + file.length());
            }
        }
    }

    //delete database
    public void db_delete() {
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        if (file.exists()) {
            file.delete();
            System.out.println("delete database file.");
        }
    }


    @Override
    public synchronized void close() {
        if (db != null)
            db.close();

        super.close();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        Dlog.d("db", "111");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            Log.v("Database Upgrade", "Database version higher than old.");
            db_delete();
        }

    }

    public int getTabledata(){
        Cursor cursor = null;
        db = getWritableDatabase();
        int count = 0;
        cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (cursor.moveToFirst()) {
            while ( !cursor.isAfterLast() ) {
                Dlog.d("db","Table Name=> "+cursor.getString(0));
                cursor.moveToNext();
                count++;
            }
        }

        return count;

    }


}

