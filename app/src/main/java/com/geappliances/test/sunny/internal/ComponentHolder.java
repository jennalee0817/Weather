package com.geappliances.test.sunny.internal;

import android.content.Context;
import android.util.Log;

import com.geappliances.test.sunny.common.Dlog;
import com.geappliances.test.sunny.db.MyWeatherDbHelper;
import com.geappliances.test.sunny.db.common.CoppiedDbHelper;
import com.geappliances.test.sunny.db.common.WeatherMapDbHelper;

/**
 * @file ComponentHolder.java
 * @date 07/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */


public class ComponentHolder {
    private final static ComponentHolder INSTANCE = new ComponentHolder();


    private CoppiedDbHelper coppiedDbHelper;
    private MyWeatherDbHelper myWeatherDbHelper;

    public static ComponentHolder getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        Dlog.d("db","초기화");
        this.myWeatherDbHelper = new MyWeatherDbHelper(context);
        this.coppiedDbHelper = new WeatherMapDbHelper(context);


    }

    public CoppiedDbHelper getCoppiedDbHelper() {
        if (coppiedDbHelper == null) {
            synchronized (ComponentHolder.class) {
                if (coppiedDbHelper == null) {
                    Dlog.d("db","초기화2");

                    coppiedDbHelper = null;
                }
            }
        }
        return coppiedDbHelper;
    }

    public MyWeatherDbHelper getMyWeatherDbHelper() {
        if (myWeatherDbHelper == null) {
            synchronized (ComponentHolder.class) {
                if (myWeatherDbHelper == null) {
                    Dlog.d("db","초기화3");

                    myWeatherDbHelper = null;
                }
            }
        }
        return myWeatherDbHelper;
    }

}