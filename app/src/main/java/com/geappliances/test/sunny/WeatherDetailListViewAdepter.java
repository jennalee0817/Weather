package com.geappliances.test.sunny;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.geappliances.test.sunny.common.Dlog;

import java.util.ArrayList;

/**
 * @file MyAdapter.java
 * @date 2019. 2. 26. FW9
 * @brief
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class WeatherDetailListViewAdepter extends RecyclerView.Adapter<WeatherDetailListViewAdepter.ViewHolder> {
    private ArrayList<WeatherDetailData> weatherDetailDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView weatherDetailTextView;
        public ImageView weatherDetailImageView;
        public TextView weatherDetailValueTextView;

        public ViewHolder(View view) {
            super(view);
            weatherDetailTextView = (TextView)view.findViewById(R.id.WeatherDetailTitle);
            weatherDetailImageView = (ImageView)view.findViewById(R.id.WeatherDetailImage);
            weatherDetailValueTextView = (TextView)view.findViewById(R.id.WeatherDetailValue);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public WeatherDetailListViewAdepter(ArrayList<WeatherDetailData> myDataset) {
        weatherDetailDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new card view
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_weatherdetail, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder viewHolder = new ViewHolder(cardView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.weatherDetailTextView.setText(weatherDetailDataset.get(position).getWeatherDetailText());
        holder.weatherDetailImageView.setImageResource(weatherDetailDataset.get(position).getWeatherDetailImage());
        Drawable drawable = holder.weatherDetailImageView.getDrawable();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
        holder.weatherDetailValueTextView.setText(weatherDetailDataset.get(position).getWeatherDetailValueText());
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return weatherDetailDataset.size();
    }
}
