package com.geappliances.test.sunny;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.geappliances.test.sunny.common.Dlog;
import com.geappliances.test.sunny.data.WeatherData;
import com.geappliances.test.sunny.internal.ComponentHolder;
import com.geappliances.test.sunny.search.SearchAddActivity;
import com.geappliances.test.sunny.search.SearchAddPresenter;
import com.geappliances.test.sunny.search.listener.SearchAdd;

public class SplashActivity extends AppCompatActivity implements SearchAdd.View {

    private SearchAddPresenter presenter;
    private String[] PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    String stringLatitude;
    String stringLongitude;
    String city;
    String country;
    String postalCode;
    String addressLine;
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter = new SearchAddPresenter(this, this);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (ConnectivityHelper.isConnectedToNetwork(this)) {
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

                goMainActivity();
            } else {
                ActivityCompat.requestPermissions(this, PERMISSION, 1);
            }
        } else {
            Toast.makeText(this, "No network", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (permissions.length > 0) {
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                       goMainActivity();

//                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
//                        Intent intent = new Intent(getApplicationContext(), SearchAddActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
                } else {
                    Toast.makeText(this, "수신 권한 부여받지 못함", Toast.LENGTH_LONG).show();
//                    gpsTracker.showSettingsAlert();
                }
        }
    }

    @Override
    public void successGettingList(WeatherData data) {
        WeatherData mydata = data;
        mydata.setSeq(0); // is current location
        ComponentHolder.getInstance().getMyWeatherDbHelper().updateCurrentItem(mydata);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("currentWeather", data);
        startActivity(intent);
        finish();
    }

    @Override
    public void showErrorGettingList(String errorMsg) {
        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG);
        finish();
    }

    public static class ConnectivityHelper {
        public static boolean isConnectedToNetwork(Context context) {
            ConnectivityManager connectivityManager;
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            boolean isConnected = false;
            if (connectivityManager != null) {
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
            }
            return isConnected;
        }
    }

    public boolean getLocation() {
        gpsTracker = new GPSTracker(this);
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            stringLatitude = String.valueOf(gpsTracker.latitude);
            stringLongitude = String.valueOf(gpsTracker.longitude);
            city = gpsTracker.getLocality(this);
            country = gpsTracker.getCountryName(this);
            postalCode = gpsTracker.getPostalCode(this);
            addressLine = gpsTracker.getAddressLine(this);
            Dlog.d("GPS value : ", city + " " + country + " " + stringLatitude + " " + stringLongitude);
            if (city == null || country == null) {
                return false;
            }
        }
        return true;
    }


    private void goMainActivity(){
        if (getLocation()) {
            presenter.searchWeather(stringLatitude, stringLongitude);
        } else {
            if(ComponentHolder.getInstance().getMyWeatherDbHelper().countAllContents()>0){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(), SearchAddActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}