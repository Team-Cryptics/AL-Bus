package com.example.punyaaachman.albus.Activities;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.punyaaachman.albus.Interfaces.MapInterface;
import com.example.punyaaachman.albus.POJO.GlobalVariables;
import com.example.punyaaachman.albus.POJO_Map.Distance;
import com.example.punyaaachman.albus.POJO_Map.Element;
import com.example.punyaaachman.albus.POJO_Map.MapData;
import com.example.punyaaachman.albus.POJO_Map.Row;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by SUPERUSER on 13-04-2017.
 */

public class MapService extends Service {


    MapInterface client;
    Double lat, lon;
    LinkedHashMap<String, String> stops_latlong;
    String BASE__URL = "https://maps.googleapis.com/";
    String API_KEY = "AIzaSyCTOFU8cjyhfufwU7jwJjxzsjQ0hGCP_14";
    int stopDistance;
    TextToSpeech textToSpeech;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("TAG", "Service Bind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Log.i("TAG", "Service Started");
        stops_latlong = new LinkedHashMap<>();
        stops_latlong.put("0", "28.6098,77.1002");
        stops_latlong.put("1", "28.6304,77.0798");
        stops_latlong.put("2", "28.675,77.0949");
        stops_latlong.put("3", "28.7246,77.128");
        stops_latlong.put("4", "28.7499,77.1183");

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE__URL).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        client = retrofit.create(MapInterface.class);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.setPitch(1 / 4);
                }
            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener listener = new OurListener();
/////////////
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(this, "Please enable permissions", Toast.LENGTH_SHORT).show();
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, listener);


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("TAG", "Service Stopped");
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


    private class OurListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            location.setAccuracy(Criteria.ACCURACY_HIGH);
            lat = location.getLatitude();
            lon = location.getLongitude();

            Log.i("TAG", "Service - location listener");
            Log.i("TAG", lat + " " + lon + " ");

            String origin = lat + "," + lon; //CURRENT LOCATION OF THE BUS
            String d = Integer.toString(GlobalVariables.dest);
            String destination = stops_latlong.get(d);
            Log.i("TAG", "Destination latlong is " + destination);
            calculateDistance(origin, destination);

        /*    switch(stopCode) {

                case 0: destination = stops_latlong.get("1");
                    calculateDistance(origin,destination);
                    break;

                case 1: destination = stops_latlong.get("2");
                    calculateDistance(origin,destination);
                    break;

                case 2: destination = stops_latlong.get("3");
                    calculateDistance(origin,destination);
                    break;

                case 3: destination = stops_latlong.get("4");
                    calculateDistance(origin,destination);
                    break;

            } */


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

        void calculateDistance(String origin, String destination) {

            Call<MapData> data = client.getMapData(origin, destination, "driving", API_KEY);
            data.enqueue(new Callback<MapData>() {
                @Override
                public void onResponse(Call<MapData> call, Response<MapData> response) {
                    MapData mapData = response.body();
                    Log.i("TAG", mapData.getStatus());

                    List<Row> rowData = mapData.getRows();
                    Row row = rowData.get(0);
                    List<Element> elementList = row.getElements();
                    Distance distance = elementList.get(0).getDistance();
                    stopDistance = distance.getValue();
                    Log.i("TAG", "Distance - " + stopDistance);

                    if (stopDistance < 800) {

                        String toSpeak = "You are about to reach your destination";
                        Vibrator vibrate = (Vibrator)getApplication().getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        vibrate.vibrate(5000);
                        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                        textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

                        if (stopDistance < 500) {
                            String reached = "Kindly ensure you board off at your destination";
                            textToSpeech.speak(reached, TextToSpeech.QUEUE_FLUSH, null);

                            Intent serviceIntent = new Intent(MapService.this, DefaulterService.class);
                            startService(serviceIntent);
                            stopSelf();


                        }
                    }


                }

                @Override
                public void onFailure(Call<MapData> call, Throwable t) {
                    Log.i("TAG", "FAIL");
                }
            });


        }
    }

}

