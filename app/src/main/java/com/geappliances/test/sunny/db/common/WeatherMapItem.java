package com.geappliances.test.sunny.db.common;

/**
 * @file WeatherMapItem.java
 * @date 07/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class WeatherMapItem {

    public static final String DATABASE_SCHEME_ID = "ID";
    public static final String DATABASE_SCHEME_MEANING = "Meaning";
    public static final String DATABASE_SCHEME_ICON = "Icon";

    private int id;
    private String meaning;
    private String icon;

    WeatherMapItem(int id, String meaning, String icon){
        this.id = id;
        this.meaning = meaning;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getMeaning() {
        return meaning;
    }
}
