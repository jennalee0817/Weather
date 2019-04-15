package com.geappliances.test.sunny.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    public static String AppId = "5537b3aadbbbd475c7e8a2f6d9714b7b";
    public static final String BASE_URL = "https://api.openweathermap.org";
    public static final String TIME_BASE_URL = "http://api.timezonedb.com";
    public static final String TIME_API_KEY = "QID7N4GNZIWJ";
    public static Retrofit retrofit;

    /*
    This public static method will return Retrofit client
    anywhere in the appplication
    */
    public static Retrofit getRetrofitClient() {
        //If condition to ensure we don't create multiple retrofit instances in a single application
        if (retrofit == null) {
            //Defining the Retrofit using Builder
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) //This is the only mandatory call on Builder object.
                    .addConverterFactory(GsonConverterFactory.create()) // Convertor library used to convert response into POJO
                    .build();
        }
        return retrofit;
    }
}
