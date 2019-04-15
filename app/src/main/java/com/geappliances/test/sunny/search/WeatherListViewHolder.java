package com.geappliances.test.sunny.search;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geappliances.test.sunny.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @file WeatherListViewHolder.java
 * @date 19/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class WeatherListViewHolder extends RecyclerView.ViewHolder {

    private TextView txtCity;
    private TextView txtTime;
    private TextView txtTemp;
    private ImageView imgWeather;
    private View itemView;


    public WeatherListViewHolder(@NonNull View itemView) {
        super(itemView);
        txtCity = itemView.findViewById(R.id.txt_city);
        txtTemp = itemView.findViewById(R.id.txt_temp);
        txtTime = itemView.findViewById(R.id.txt_time);
        imgWeather = itemView.findViewById(R.id.img_weather);
        this.itemView = itemView;
    }

    public void setCity(String city) {
        txtCity.setText(city);
    }

    public void setTime(long time, String timeZone) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        txtTime.setText(simpleDateFormat.format(time));
    }

    public void setTemp(int temp) {
        txtTemp.setText(temp-273 + "°");
    }


    public void setImgWeather(Drawable imgWeather) {
        this.imgWeather.setImageDrawable(imgWeather);
    }

    public View getItemView() {
        return itemView;
    }

}
