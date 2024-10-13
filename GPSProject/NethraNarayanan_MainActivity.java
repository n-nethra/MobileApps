package com.example.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener{

    TextView latitudeText, longitudeText, addressText, distanceText, timeText, topAddressesText, topTimeText;
    ArrayList<Location> locations = new ArrayList<Location>();
    ArrayList <Address> addresses = new ArrayList<>();
    ArrayList<Double> times = new ArrayList<Double>();
    long startTime = 0;
    double totalDistance = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitudeText = findViewById(R.id.id_latitudeText);
        longitudeText = findViewById(R.id.id_longitudeText);
        addressText = findViewById(R.id.id_addressText);
        distanceText = findViewById(R.id.id_distanceText);
        timeText = findViewById(R.id.id_times);
        topAddressesText = findViewById(R.id.id_addresses);
        topTimeText = findViewById(R.id.id_times);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkLocationPermission(this);

        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);

    }


    public void checkLocationPermission(Activity activity){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

        }
        else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

        }
        else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                locations.add(location);

                //longitude and latitude
                latitudeText.setText("latitude: \n" + location.getLatitude() + "");
                longitudeText.setText("longitude: \n" + location.getLongitude() + "");

                //distance between current and prev location
                if (locations.size() > 1) {
                    totalDistance += location.distanceTo(locations.get(locations.size() - 2));
                }

                distanceText.setText("Total distance: \n" + totalDistance * 0.000621371 + " miles");

                //address
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.US);
                try {
                    List<Address> adds = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    addresses.add(adds.get(adds.size()-1));
                    addressText.setText(adds.get(0).getAddressLine(0));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e){
                    Log.d("TAG_EXCEPTIONSS", "" + e);
                }

                //time spent at current location
                if (startTime == 0) {
                    startTime = SystemClock.elapsedRealtime();
                }
                else {
                    long currentTime = SystemClock.elapsedRealtime();
                    long totalTime = currentTime - startTime;
                    times.add((double) (totalTime/1000.0));
                    startTime = SystemClock.elapsedRealtime();
                }


                //top addresses & times
                ArrayList<Address> topAddresses = (ArrayList<Address>) addresses.clone();
                ArrayList<Double> topTimes = (ArrayList<Double>) times.clone();

                for (int i = 0; i < topTimes.size()-1; i++){
                    int max = i;
                    for (int j = i+1; j < topTimes.size(); j++){
                        if (topTimes.get(j) > topTimes.get(max)){
                            max = j;
                        }
                    }

                    double tempTime = topTimes.get(i);
                    topTimes.set(i, topTimes.get(max));
                    topTimes.set(max, tempTime);

                    Address tempAddress = topAddresses.get(i);
                    topAddresses.set(i, topAddresses.get(max));
                    topAddresses.set(max, tempAddress);

                }


                Log.d("TAG_SORTING", topTimes.toString() + "\n" + topAddresses.toString());


                String addressString = "";
                String timeString = "";

                if (topTimes.size() == 1){
                    addressString = "1: " + topAddresses.get(0).getAddressLine(0);
                    timeString = "1: " +  topTimes.get(0);

                   // Log.d("TAG_ADDRESSCHECK1", addressString);
                }
                if (topTimes.size() == 2){
                    addressString = "1: " + topAddresses.get(0).getAddressLine(0) + "\n2: " + topAddresses.get(1).getAddressLine(0);
                    timeString += "1: " +  topTimes.get(0) + "\n2: " + topTimes.get(1);

                    //Log.d("TAG_ADDRESSCHECK2", addressString);
                }
                if (topTimes.size() >= 3){
                    addressString = "1: " + topAddresses.get(0).getAddressLine(0) + "\n2: " + topAddresses.get(1).getAddressLine(0)+ "\n3: " + topAddresses.get(2).getAddressLine(0);
                    timeString = "1: " +  topTimes.get(0) + "\n2: " + topTimes.get(1) + "\n3: " + topTimes.get(2);

                    //Log.d("TAG_ADDRESSCHECK3", addressString);
                }

                //Log.d("TAG_ADDRESSCHECK", "" + topAddresses.get(topAddresses.size()-1).getAddressLine(0));
                //Log.d("TAG_ADDRESSCHECKtot", addressString);

                topAddressesText.setText(addressString + "");
                topTimeText.setText(timeString + "");


            }
        }, 3000);



    }



}