package com.geappliances.test.sunny.db.common;

        import java.util.ArrayList;

/**
 * @file CoppiedDbHelper.java
 * @date 07/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public interface CoppiedDbHelper {


    WeatherMapItem findWeather(int id);

    ArrayList<CityItem> findCityList(String cityName, int limit, int offset);

    CityItem findCityItem(int Cityid);
}
