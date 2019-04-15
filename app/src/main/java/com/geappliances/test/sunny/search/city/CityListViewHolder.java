package com.geappliances.test.sunny.search.city;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.geappliances.test.sunny.R;

import java.text.SimpleDateFormat;

import static android.support.constraint.Constraints.TAG;

/**
 * @file WeatherListViewHolder.java
 * @date 19/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class CityListViewHolder extends RecyclerView.ViewHolder{

    private TextView txtCity;
    private View itemView;

    public CityListViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
        txtCity = itemView.findViewById(R.id.txt_cityname);
    }

    public void setCity(String city) {
        txtCity.setText(city);
    }

    public View getItemView() {
        return itemView;
    }
}
