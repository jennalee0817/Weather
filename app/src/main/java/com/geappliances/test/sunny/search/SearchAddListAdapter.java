package com.geappliances.test.sunny.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geappliances.test.sunny.R;
import com.geappliances.test.sunny.data.WeatherData;
import com.geappliances.test.sunny.db.common.WeatherMapItem;
import com.geappliances.test.sunny.internal.ComponentHolder;
import com.geappliances.test.sunny.search.listener.WeatherlistClickListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * @file SearchAddListAdapter.java
 * @date 19/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class SearchAddListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<WeatherData> weatherList;
    private Context context;
    private WeatherlistClickListener weatherlistClickListener;

    SearchAddListAdapter(Context context, ArrayList<WeatherData> list, WeatherlistClickListener weatherlistClickListener) {
        this.weatherList = list;
        this.context = context;
        this.weatherlistClickListener = weatherlistClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_weatherlist, viewGroup, false);
        return new WeatherListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {

        if (weatherList.isEmpty()) {
            return;
        }
        WeatherData weatherData = weatherList.get(i);
        WeatherListViewHolder weatherListViewHolder = (WeatherListViewHolder) viewHolder;

        weatherListViewHolder.setCity(weatherData.getCity() + ", " + weatherData.getCountry());
        weatherListViewHolder.setTemp((int) weatherData.getTemp());
        long date = System.currentTimeMillis();
        weatherListViewHolder.setTime(date, weatherData.getTimeZone());
        WeatherMapItem item = ComponentHolder.getInstance().getCoppiedDbHelper().findWeather(weatherData.getId());
        weatherListViewHolder.setImgWeather(context.getResources().getDrawable(weatherData.getWeatherImg(item.getIcon())));

        weatherListViewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherlistClickListener.onClick(v,viewHolder.getAdapterPosition());
            }
        });



    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }


    public void changeDataList(ArrayList<WeatherData> list){
        this.weatherList = list;
        notifyDataSetChanged();
    }

    public void removePosition(int position){
        WeatherData weatherData = weatherList.get(position);
        ComponentHolder.getInstance().getMyWeatherDbHelper().removeExist(weatherData.getCityId());
        weatherList.remove(position);
        notifyDataSetChanged();
    }


}
