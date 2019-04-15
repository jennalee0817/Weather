package com.geappliances.test.sunny.db.common;

/**
 * @file CityItem.java
 * @date 20/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class CityItem {
    public static final String DATABASE_SCHEME_CITY_ID = "id";
    public static final String DATABASE_SCHEME_CITY_NAME = "name";
    public static final String DATABASE_SCHEME_COUNTRY_NAME = "country_name";
    public static final String DATABASE_SCHEME_LONGITUDE = "logitude";
    public static final String DATABASE_SCHEME_LATITUDE = "latitude";

    String cityName;
    int cityId;
    String countryName;
    float longitude;
    float latitude;

    public CityItem(String cityName, int cityId, String countryName, float longitude, float latitude) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.countryName = countryName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
