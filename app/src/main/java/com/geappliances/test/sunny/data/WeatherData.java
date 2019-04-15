package com.geappliances.test.sunny.data;

import com.geappliances.test.sunny.R;

import java.io.Serializable;

/**
 * @file WeatherData.java
 * @date 07/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class WeatherData implements Serializable {

    //location
    private int cityId;

    private String country = "KR"; //default: Korea
    private String city = "Seoul"; //default: Seoul

    private double lat;
    private double lon;

    private long date;

    //weather Information
    private int id; //weather code

    private String timeZone;

    private double temp; //current temperature
    private double tempMin; //minimum temperature
    private double tempMax; //maximum temperature

    private float humidity; // %

    private float windDig; //wind Direction
    private double windSpeed; // m/s

    private float pressure; //hPa

    private long sunrise;
    private long sunset;

    private int seq;

    public WeatherData() {

    }

    public WeatherData(int cityId, String country, double lat, double lon, String city, long date, int id, double temp, double tempMin, double tempMax, float humidity, float windDig, double windSpeed, float pressure, long sunrise, long sunset, int seq) {
        this.cityId = cityId;
        this.lat = lat;
        this.lon = lon;
        this.country = country;
        this.city = city;
        this.date = date;
        this.id = id;
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = humidity;
        this.windDig = windDig;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.seq = seq;
    }

    public int getCityId() {
        return cityId;
    }

    public int getId() {
        return id;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public double getTemp() {
        return temp;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public float getWindDig() {
        return windDig;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public long getDate() {
        return date;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public int getSeq() {
        return seq;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public void setWindDig(int windDig) {
        this.windDig = windDig;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setWindDig(float windDig) {
        this.windDig = windDig;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isEvening() {
        long now = System.currentTimeMillis();

        if (now > sunset || now < sunrise) {
            return true;
        }

        return false;
    }

    public int getWeatherImg(String id) {
        String icon = id;
        if (id.isEmpty()) {
            icon = "clear";
        }

        switch (icon) {
            case "cloud1":
                if (isEvening()) {
                    return R.drawable.ic_cloud1_n;
                } else {

                    return R.drawable.ic_cloud1_d;
                }
            case "cloud2":
                if (isEvening()) {
                    return R.drawable.ic_cloud2_n;
                } else {

                    return R.drawable.ic_cloud2_d;
                }
            case "cloud3":
                if (isEvening()) {
                    return R.drawable.ic_cloud3_n;
                } else {

                    return R.drawable.ic_cloud3_d;
                }
            case "cloud4":
                return R.drawable.ic_clouds;

            case "rain1":
            case "rain2":
            case "rain3":
            case "rain4":
                if (isEvening()) {
                    return R.drawable.anim_rain3_n;
                } else {
                    return R.drawable.ic_rain3_d;
                }

            case "snow1":
            case "snow2":
            case "snow3":
                if (isEvening()) {
                    return R.drawable.ic_snow3_n;
                } else {
                    return R.drawable.ic_snow3_d;
                }
            case "thunder":
                return R.drawable.ic_thunder;

            case "mist":
                return R.drawable.ic_mist;
            default:
                if (isEvening()) {
                    return R.drawable.ic_clear_n;
                } else {
                    return R.drawable.ic_clear_d;
                }

        }
    }

    public int getWeatherAnim(String id) {
        String icon = id;
        int animVector;
        if (id.isEmpty()) {
            icon = "clear";
        }
        switch (icon) {
            case "cloud1":
                if (isEvening()) {
                    animVector = R.drawable.anim_cloud1_n;
                } else {
                    animVector = R.drawable.anim_cloud1_d;
                }
                break;
            case "cloud2":
                if (isEvening()) {
                    animVector = R.drawable.anim_cloud2_n;
                } else {
                    animVector = R.drawable.anim_cloud2_d;
                }
                break;
            case "cloud3":
                if (isEvening()) {
                    animVector = R.drawable.anim_cloud3_n;
                } else {
                    animVector = R.drawable.anim_cloud3_d;
                }
                break;
            case "cloud4":
                animVector = R.drawable.anim_clouds;
                break;
            case "rain1":
            case "rain2":
            case "rain3":
            case "rain4":
                if (isEvening()) {
                    animVector = R.drawable.anim_rain3_n;
                } else {
                    animVector = R.drawable.anim_rain3_d;
                }
                break;
            case "snow1":
            case "snow2":
            case "snow3":
                if (isEvening()) {
                    animVector = R.drawable.anim_snow3_n;
                } else {
                    animVector = R.drawable.anim_snow3_d;

                }
                break;
            case "thunder":
                animVector = R.drawable.anim_thunder;
                break;
            case "mist":
                animVector = R.drawable.anim_mist;
                break;
            default:
                if (isEvening()) {
                    animVector = R.drawable.anim_clear_n;
                } else {
                    animVector = R.drawable.anim_clear_d;
                }
                break;
        }
        return animVector;
    }

    public int getBackgroundColor(String id) {
        String icon = id;
        int color;
        if (id.isEmpty()) {
            icon = "clear";
        }
        if (isEvening()) {
            color = R.color.sky_night;
            return color;
        }
        switch (icon) {
            case "cloud1":
                color = R.color.sky_day;
                break;
            case "cloud2":
                color = R.color.sky_day;
                break;
            case "cloud3":
                color = R.color.sky_cloud;
                break;
            case "cloud4":
                color = R.color.sky_cloud;
                break;
            case "rain1":
            case "rain2":
            case "rain3":
            case "rain4":
                color = R.color.sky_cloud;
                break;
            case "snow1":
            case "snow2":
            case "snow3":
                color = R.color.sky_cloud;
                break;
            case "thunder":
                color = R.color.sky_cloud;
                break;
            case "mist":
                color = R.color.sky_cloud;
                break;
            default:
                color = R.color.sky_day;
                break;
        }
        return color;
    }

    public int getWeatherDetailImg(String id) {
        String icon = id;
        if (id.isEmpty()) {
            icon = "clear";
        }

        switch (icon) {
            case "cloud1":
                return R.drawable.ic_cloud1_d;
            case "cloud2":
                return R.drawable.ic_cloud2_d;
            case "cloud3":
                return R.drawable.ic_cloud3_d;

            case "cloud4":
                return R.drawable.ic_clouds;

            case "rain1":
            case "rain2":
            case "rain3":
            case "rain4":
                return R.drawable.ic_rain3_d;

            case "snow1":
            case "snow2":
            case "snow3":
                return R.drawable.ic_snow3_d;

            case "thunder":
                return R.drawable.ic_thunder;

            case "mist":
                return R.drawable.ic_mist;

            default:
                return R.drawable.ic_clear_d;

        }
    }

    public String getWindDirection() {
        String[] directions = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW", "N"};
        return directions[(int) Math.round((((double) windDig % 360) / 45))];

    }
}

