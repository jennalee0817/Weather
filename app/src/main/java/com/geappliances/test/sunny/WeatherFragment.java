package com.geappliances.test.sunny;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geappliances.test.sunny.common.Dlog;
import com.geappliances.test.sunny.data.WeatherData;
import com.geappliances.test.sunny.db.common.WeatherMapItem;
import com.geappliances.test.sunny.internal.ComponentHolder;
import com.geappliances.test.sunny.network.data.Sys;
import com.geappliances.test.sunny.search.ListItemDecoration;
import com.geappliances.test.sunny.search.SearchAddActivity;
import com.geappliances.test.sunny.search.SearchAddPresenter;

import com.geappliances.test.sunny.search.listener.SearchAdd;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.http.HEAD;

/**
 * @file WeatherFragment.java
 * @date 08/02/2019
 * @brief fragment to show weather information
 * @copyright GE Appliances, a Haier Company (Confidential). All rights reserved.
 */
public class WeatherFragment extends Fragment implements SearchAdd.View {

    private String TAG = "WeatherFragment";
    private Context context;

    private WeatherData data;
    private WeatherMapItem item;
    private Button addButton;
    private Button shareButton;
    private TextView cityTextView;
    private TextView tempTextView;
    private TextView descriptionTextView;
    private RecyclerView recyclerView;

    int measure_temp = 273;

    AnimatedVectorDrawableCompat animVector;
    ImageView weatherImageView;

    SearchAddPresenter searchAddPresenter;

    WeatherDetailData weatherDetailData;

    public static WeatherFragment create(WeatherData data) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchAddPresenter = new SearchAddPresenter(getActivity(), this);
        data = (WeatherData) getArguments().getSerializable("data");
        if (System.currentTimeMillis() - data.getDate() > 600000) { //pause 10min btw call api
            Dlog.d(TAG, data.getId() + data.getCity());
            searchAddPresenter.searchWeather(data.getCityId());
        }
        Dlog.d(TAG, data.getId() + data.getCity() + "  " + (System.currentTimeMillis() - data.getDate()) + TimeZone.getTimeZone(data.getCity() + "/" + data.getCountry()));
        item = ComponentHolder.getInstance().getCoppiedDbHelper().findWeather(data.getId());
        Dlog.d(TAG, item.getId() + item.getMeaning());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_weather, container, false);
        rootView.setBackgroundColor(ContextCompat.getColor(getActivity(), data.getBackgroundColor(item.getIcon())));
        addButton = (Button) rootView.findViewById(R.id.btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchAddActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

            }
        });

        final int temp = (int) (data.getTemp() - measure_temp);

        shareButton = (Button) rootView.findViewById(R.id.btn_share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takeScreenshot();

            }
        });

        cityTextView = (TextView) rootView.findViewById(R.id.txt_where);
        if (data.getSeq() == 0) {
            cityTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_location_on, 0, 0, 0);
        } else {
            cityTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        tempTextView = (TextView) rootView.findViewById(R.id.txt_temp);
        descriptionTextView = (TextView) rootView.findViewById(R.id.txt_description);

        cityTextView.setText(data.getCity() + ", " + data.getCountry());
        descriptionTextView.setText(item.getMeaning());
        tempTextView.setText(temp + "°");

        SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");
        timeFormat.setTimeZone(TimeZone.getTimeZone(data.getTimeZone()));

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new ListItemDecoration(50));

        int humidity = (int) (data.getHumidity());
        int pressure = (int) (data.getPressure());
        int tempMin = (int) (data.getTempMin());
        int tempMax = (int) (data.getTempMax());
        int windSpeed = (int) (data.getWindSpeed());
        int windDig = (int) (data.getWindDig());
        String sunRise = timeFormat.format(data.getSunrise());
        String sunSet = timeFormat.format(data.getSunset());


        ArrayList<WeatherDetailData> weatherDetailDataList = new ArrayList<>();
        Log.d("getContext()", String.valueOf( getContext() ));
        weatherDetailData = new WeatherDetailData("Humidity", R.drawable.ic_humidity, humidity + "%");
        weatherDetailDataList.add(weatherDetailData);
        weatherDetailData = new WeatherDetailData("Pressure", R.drawable.ic_press, pressure +"hPa");
        weatherDetailDataList.add(weatherDetailData);
        weatherDetailData = new WeatherDetailData("Sunrise", R.drawable.anim_sunrise, sunRise);
        weatherDetailDataList.add(weatherDetailData);
        weatherDetailData = new WeatherDetailData("Sunset", R.drawable.anim_sunset, sunSet);
        weatherDetailDataList.add(weatherDetailData);

        weatherDetailData = new WeatherDetailData("Wind", R.drawable.ic_wind_speed, windSpeed + "m/s");
        weatherDetailDataList.add(weatherDetailData);
        weatherDetailData = new WeatherDetailData("Wind Dig", R.drawable.ic_wind_dir, data.getWindDirection());
        weatherDetailDataList.add(weatherDetailData);
        weatherDetailData = new WeatherDetailData("Temp Min", R.drawable.anim_temp_min, (tempMin - measure_temp) +  "°");
        weatherDetailDataList.add(weatherDetailData);
        weatherDetailData = new WeatherDetailData("Temp Max", R.drawable.anim_temp_max, (tempMax - measure_temp) +  "°");
        weatherDetailDataList.add(weatherDetailData);

        WeatherDetailListViewAdepter myAdapter = new WeatherDetailListViewAdepter(weatherDetailDataList);
        recyclerView.setAdapter(myAdapter);

        weatherImageView = (ImageView) rootView.findViewById(R.id.img_weather);
        weatherImageView.setImageDrawable(getResources().getDrawable(data.getWeatherImg(item.getIcon())));

        animVector = AnimatedVectorDrawableCompat.create(getContext(), data.getWeatherAnim(item.getIcon()));
        weatherImageView.setImageDrawable(animVector);
        animVector.start();

        return rootView;
    }

    @Override
    public void successGettingList(WeatherData data) {
        this.data = data;
        ComponentHolder.getInstance().getMyWeatherDbHelper().update(data);
    }

    @Override
    public void showErrorGettingList(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }


    private void takeScreenshot() {

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(),"App needs a permission for storage access to send screenshot\nNow sending only text",Toast.LENGTH_LONG).show();
            sendText();
            return;
        }

        try {
            String path = Environment.getExternalStorageDirectory().toString() + "/" + "weather" + ".jpg";

            // create bitmap screen capture
            View view = getActivity().getWindow().getDecorView().getRootView();
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            sendImage(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            sendText();
            e.printStackTrace();
        }
    }


    private void sendText() {
        Dlog.d("debug", "텍스트");
        int temp = (int) (data.getTemp() - measure_temp);
        String shareBody = "Weather in " + data.getCity() + ", " + data.getCountry() + " is " + item.getMeaning() + "(" + temp + "°)";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HOW IS THE WEATHER?");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "SUNNY WEATHERCAST"));
    }

    private void sendImage(File path) {
        Dlog.d("debug", "이미지 " + path);
        int temp = (int) (data.getTemp() - measure_temp);
        String shareBody = "Weather in " + data.getCity() + ", " + data.getCountry() + " is " + item.getMeaning() + "(" + temp + "°)";
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "HOW IS THE WEATHER?");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);

        sharingIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(getActivity(), "com.geappliances.test.sunny.fileprovider", path));
        startActivity(Intent.createChooser(sharingIntent, "SUNNY WEATHERCAST"));

    }
}
