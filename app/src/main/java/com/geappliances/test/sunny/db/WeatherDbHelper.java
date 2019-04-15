package com.geappliances.test.sunny.db;

import com.geappliances.test.sunny.data.WeatherData;

import java.util.ArrayList;

/**
 * @file WeatherDbHelper.java
 * @date 08/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public interface WeatherDbHelper {

    WeatherData find(int id);

    void insert(WeatherData model);

    void removeExist(int id);

    void update(WeatherData model);

    void updateCurrentItem(WeatherData model);

    void remove(int id);

    int countAllContents();



    ArrayList<WeatherData> fetchAllContent();

}