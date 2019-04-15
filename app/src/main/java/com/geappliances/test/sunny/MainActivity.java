package com.geappliances.test.sunny;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.geappliances.test.sunny.common.Dlog;
import com.geappliances.test.sunny.data.WeatherData;
import com.geappliances.test.sunny.internal.ComponentHolder;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private ArrayList<WeatherData> weatherData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Dlog.d("테스트", " 메인");
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);

    }

    @Override
    protected void onResume() {
        Intent intent = getIntent();
        weatherData = new ArrayList<>();
        weatherData = ComponentHolder.getInstance().getMyWeatherDbHelper().fetchAllContent();
        int position = 0;
        if (intent.getIntExtra("setPosition", -1) != -1) {
            position = intent.getIntExtra("setPosition", -1);
            intent.removeExtra("setPosition");
        } else {
            position = viewPager.getCurrentItem();
        }
        viewPager.setAdapter(pagerAdapter);
        Dlog.d("Lucy", weatherData.get(0).getCity());
        viewPager.setCurrentItem(position, true);
        super.onResume();
    }

    private class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return WeatherFragment.create(weatherData.get(position));
        }

        @Override
        public int getCount() {
            if (weatherData.isEmpty())
                return 1;
            return weatherData.size();
        }


    }
}
