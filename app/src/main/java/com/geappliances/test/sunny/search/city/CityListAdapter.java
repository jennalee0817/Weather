package com.geappliances.test.sunny.search.city;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geappliances.test.sunny.R;
import com.geappliances.test.sunny.common.Util;
import com.geappliances.test.sunny.db.common.CityItem;
import com.geappliances.test.sunny.search.listener.CitylistClickListener;
import com.geappliances.test.sunny.search.listener.OnBottomReachedListener;

import java.util.ArrayList;

/**
 * @file SearchAddListAdapter.java
 * @date 19/02/2019
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class CityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CityItem> cityList;
    private CitylistClickListener citylistClickListener;
    private OnBottomReachedListener onBottomReachedListener;

    public CityListAdapter(ArrayList<CityItem> list, CitylistClickListener citylistClickListener) {
        this.cityList = list;
        this.citylistClickListener = citylistClickListener;
    }

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        this.onBottomReachedListener = onBottomReachedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_citylist, viewGroup, false);
        return new CityListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (cityList.isEmpty()) {
            return;
        }
        final CityItem cityItem = cityList.get(i);
        CityListViewHolder cityListViewHolder = (CityListViewHolder) viewHolder;
        cityListViewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citylistClickListener.onClick(v,cityItem);
            }
        });

        cityListViewHolder.setCity(cityItem.getCityName() + ", " + cityItem.getCountryName());

        if (cityList.size() % Util.SIZE_LIMIT_CITYLIST == 0 && i == cityList.size() - 1){
            onBottomReachedListener.onBottomReached(i);
        }

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }


    public void changeDataList(ArrayList<CityItem> list){
        this.cityList = list;
        notifyDataSetChanged();
    }


}
