package com.geappliances.test.sunny.search.listener;

import com.geappliances.test.sunny.data.WeatherData;

/**
 * @file SearchAdd.java
 * @date 20/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public interface SearchAdd {

    interface Presenter {
        void searchWeather(int cityId);

        void searchWeather(String lat, String lon);
    }

    interface View {
        void successGettingList(WeatherData data);

        void showErrorGettingList(String errorMsg);

    }
}
