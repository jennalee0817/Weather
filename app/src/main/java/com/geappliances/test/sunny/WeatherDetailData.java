package com.geappliances.test.sunny;

import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.widget.ImageView;

/**
 * @file WeatherDetailData.java
 * @date 2019. 2. 26. FW9
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
class WeatherDetailData{
    public String weatherDetailText;
    public int weatherDetailImage;
    public String weatherDetailValueText;

    public WeatherDetailData(String weatherDetailText, int weatherDetailImage, String weatherDetailValueText){
        this.weatherDetailText = weatherDetailText;
        this.weatherDetailImage = weatherDetailImage;
        this.weatherDetailValueText = weatherDetailValueText;
    }

    public String getWeatherDetailText() {
        return weatherDetailText;
    }

    public String getWeatherDetailValueText() {
        return weatherDetailValueText;
    }

    public int getWeatherDetailImage() {
        return weatherDetailImage;
    }
}