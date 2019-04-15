package com.geappliances.test.sunny.common;

import android.app.Application;

import com.geappliances.test.sunny.internal.ComponentHolder;

/**
 * @file MainApplication.java
 * @date 07/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ComponentHolder.getInstance().init(getApplicationContext());


    }

}