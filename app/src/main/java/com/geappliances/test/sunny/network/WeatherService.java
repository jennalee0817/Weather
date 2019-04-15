package com.geappliances.test.sunny.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("/data/2.5/weather?")
    Call<WeatherResponse> getCurrentWeatherData(@Query("id") String cityId, @Query("appid") String app_id);

    @GET("/data/2.5/weather?")
    Call<WeatherResponse> getSearchWeatherData(@Query("q") String countryCode, @Query("appid") String app_id);

    @GET("/data/2.5/weather?")
    Call<WeatherResponse> getGpsWeatherData(@Query("lat") String latitude, @Query("lon") String longitude, @Query("appid") String app_id);

    @GET("/v2.1/get-time-zone")
    Call<ResponseBody> gettimezone(@Query("key") String key, @Query("by") String by, @Query("lat") double latitude, @Query("lng") double longitude, @Query("format") String format);
}