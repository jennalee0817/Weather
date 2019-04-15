package com.geappliances.test.sunny.common;

import android.util.Log;

import com.geappliances.test.sunny.BuildConfig;

/**
 * @file Dlog.java
 * @date 25/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class Dlog {

    static final String TAG = "SUNNY";
    static boolean DEBUG = BuildConfig.DEBUG;

    /**
     * Log Level Error
     **/
    public static final void e(String title, String message) {
        if (DEBUG) Log.e(TAG, title + buildLogMsg(message));
    }

    /**
     * Log Level Warning
     **/
    public static final void w(String message) {
        if (DEBUG) Log.w(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Information
     **/
    public static final void i(String message) {
        if (DEBUG) Log.i(TAG, buildLogMsg(message));
    }

    /**
     * Log Level Debug
     **/
    public static final void d(String title, String message) {
        if (DEBUG) Log.d(title, buildLogMsg(message));
    }

    /**
     * Log Level Verbose
     **/
    public static final void v(String message) {
        if (DEBUG) Log.v(TAG, buildLogMsg(message));
    }


    public static String buildLogMsg(String message) {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);

        return sb.toString();

    }


}