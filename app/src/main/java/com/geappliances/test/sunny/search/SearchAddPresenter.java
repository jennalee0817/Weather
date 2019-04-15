package com.geappliances.test.sunny.search;

import android.content.Context;
import android.support.annotation.NonNull;

import com.geappliances.test.sunny.common.Dlog;
import com.geappliances.test.sunny.data.WeatherData;
import com.geappliances.test.sunny.network.NetworkClient;
import com.geappliances.test.sunny.network.WeatherResponse;
import com.geappliances.test.sunny.network.WeatherService;
import com.geappliances.test.sunny.search.listener.SearchAdd;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.geappliances.test.sunny.network.NetworkClient.AppId;
import static com.geappliances.test.sunny.network.NetworkClient.TIME_API_KEY;

/**
 * @file SearchAddPresenter.java
 * @date 20/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class SearchAddPresenter implements SearchAdd.Presenter {
    SearchAdd.View view;
    Context context;

    public SearchAddPresenter(Context context, SearchAdd.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void searchWeather(final int cityId) {


        Dlog.d("테스트", "getCurrentDataWithCityId " + cityId);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService data = retrofit.create(WeatherService.class);

        Call<WeatherResponse> call = data.getCurrentWeatherData(Integer.toString(cityId), AppId);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                Dlog.d("테스트", "getCurrentData" + response.code() + response.message());

                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    WeatherData wData = new WeatherData(
//                            cityId,
                            weatherResponse.id,
                            weatherResponse.sys.country,
                            weatherResponse.coord.lat,
                            weatherResponse.coord.lon,
                            weatherResponse.name,
                            (long) weatherResponse.dt,
                            weatherResponse.weather.get(0).id,
                            weatherResponse.main.temp,
                            weatherResponse.main.temp_min,
                            weatherResponse.main.temp_max,
                            weatherResponse.main.humidity,
                            weatherResponse.wind.deg,
                            weatherResponse.wind.speed,
                            weatherResponse.main.pressure,
                            weatherResponse.sys.sunrise,
                            weatherResponse.sys.sunset,
                            1

                    );

                    getLocaltime(wData);
                    Dlog.d("테스트", "데이터 불러와짐" + weatherResponse.name + "   " + cityId);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Dlog.d("테스트", "getCurrentData22222" + t.getMessage());
                    view.showErrorGettingList("Network Error, can't service application now");
//                textView.setText( t.getMessage() );

            }
        });
    }

    public void getLocaltime(final WeatherData wData) {
        Dlog.d("테스트", "getLocaltime");
        final WeatherData newData = wData;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkClient.TIME_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService data = retrofit.create(WeatherService.class);
        Call<ResponseBody> call = data.gettimezone(TIME_API_KEY, "position", wData.getLat(), wData.getLon(), "json");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Dlog.d("테스트", "getLocaltime" + response.code());

                if (response.code() == 200) {
                    String result = null;
                    try {
                        result = response.body().string();
                        JSONObject jsonObject = new JSONObject(result);
                        String zoneName = jsonObject.getString("zoneName");

                        newData.setTimeZone(zoneName);
                        view.successGettingList(newData);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Dlog.d("테스트", "getCurrentData22222" + t.getMessage());
                view.showErrorGettingList("Network Error, can't service application now");

//                textView.setText( t.getMessage() );

            }
        });
    }

    @Override
    public void searchWeather(final String lat, final String lon) {
        Dlog.d("테스트", "getCurrentDataWithGps");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService data = retrofit.create(WeatherService.class);

        Call<WeatherResponse> call = data.getGpsWeatherData(lat, lon, AppId);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                Dlog.d("테스트", "getCurrentData" + response.code());

                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    WeatherData wData = new WeatherData(
//                            cityId,
                            weatherResponse.id,
                            weatherResponse.sys.country,
                            weatherResponse.coord.lat,
                            weatherResponse.coord.lon,
                            weatherResponse.name,
                            (long) weatherResponse.dt,
                            weatherResponse.weather.get(0).id,
                            weatherResponse.main.temp,
                            weatherResponse.main.temp_min,
                            weatherResponse.main.temp_max,
                            weatherResponse.main.humidity,
                            weatherResponse.wind.deg,
                            weatherResponse.wind.speed,
                            weatherResponse.main.pressure,
                            weatherResponse.sys.sunrise,
                            weatherResponse.sys.sunset,
                            1

                    );

                    getLocaltime(wData);

                    Dlog.d("테스트", "데이터 불러와짐" + weatherResponse.name + "   " + weatherResponse.sys.sunrise + "   " + weatherResponse.sys.sunset);


                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Dlog.d("테스트", "getCurrentData22222" + t.getMessage());
                view.showErrorGettingList("Network Error, can't service application now");

//                textView.setText( t.getMessage() );

            }
        });
    }


}
