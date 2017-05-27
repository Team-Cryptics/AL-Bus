package com.example.punyaaachman.albus.Activities;

import android.app.Service;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.punyaaachman.albus.POJO.GlobalVariables;
import com.example.punyaaachman.albus.POJO_Map.MapData;

import java.util.LinkedHashMap;

import retrofit2.Call;

/**
 * Created by samarthgupta on 27/05/17.
 */

public class DefaulterService extends Service {

    Double lat, lon;
    String origin; String destination;

    LinkedHashMap<String,String > stops_latlong = new LinkedHashMap<>();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {


        stops_latlong.put("0", "28.6098,77.1002");
        stops_latlong.put("1", "28.6304,77.0798");
        stops_latlong.put("2", "28.675,77.0949");
        stops_latlong.put("3", "28.7246,77.128");
        stops_latlong.put("4", "28.7499,77.1183");



        return START_STICKY;
    }


    private class OurListener implements LocationListener {










        @Override
        public void onLocationChanged(Location location) {
            location.setAccuracy(Criteria.ACCURACY_HIGH);

            lat = location.getLatitude();
            lon = location.getLongitude();

            Log.i("TAG", "Service - location listener");
            Log.i("TAG", lat + " " + lon + " ");

            origin = lat + "," + lon; //CURRENT LOCATION OF THE BUS
            String d = Integer.toString(GlobalVariables.dest);
            destination = stops_latlong.get(d);
            Log.i("TAG", "Destination latlong is " + destination);
            calculateDistance(origin, destination);
        }

        private void calculateDistance(String origin, String destination) {
            //Call<MapData> data = client.getMapData(origin, destination, "driving", API_KEY);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }
}
