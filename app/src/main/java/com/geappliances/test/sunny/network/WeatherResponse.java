package com.geappliances.test.sunny.network;

import com.geappliances.test.sunny.network.data.Clouds;
import com.geappliances.test.sunny.network.data.Coord;
import com.geappliances.test.sunny.network.data.Main;
import com.geappliances.test.sunny.network.data.Rain;
import com.geappliances.test.sunny.network.data.Sys;
import com.geappliances.test.sunny.network.data.Weather;
import com.geappliances.test.sunny.network.data.Wind;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponse {

    @SerializedName("coord")
    public Coord coord;
    @SerializedName("sys")
    public Sys sys;
    @SerializedName("weather")
    public ArrayList<Weather> weather = new ArrayList<Weather>();
    @SerializedName("main")
    public Main main;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("rain")
    public Rain rain;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("dt")
    public float dt;
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public float cod;
    @SerializedName("lastupdate")
    public long lastupdate;
}










