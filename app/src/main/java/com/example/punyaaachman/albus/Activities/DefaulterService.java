package com.example.punyaaachman.albus.Activities;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.punyaaachman.albus.Interfaces.MapInterface;
import com.example.punyaaachman.albus.POJO.CoordinatesInfo;
import com.example.punyaaachman.albus.POJO.GlobalVariables;
import com.example.punyaaachman.albus.POJO_Map.Distance;
import com.example.punyaaachman.albus.POJO_Map.Element;
import com.example.punyaaachman.albus.POJO_Map.MapData;
import com.example.punyaaachman.albus.POJO_Map.Row;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by samarthgupta on 27/05/17.
 */

public class DefaulterService extends Service {

    Double lat, lon;
    String origin; String destination;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    MapInterface client;
    int distance;

    Boolean checks [];

    int i;

    String BASE__URL = "https://maps.googleapis.com/";
    String API_KEY = "AIzaSyCTOFU8cjyhfufwU7jwJjxzsjQ0hGCP_14";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {


        checks = new Boolean[20];
        for(int j=0;j<20;j++) {
            checks[j]=true;
        }

        i=0;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();


        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener listener = new OurDefaulterListener();


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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,20000,0,listener);




        return START_NOT_STICKY;
    }


    private class OurDefaulterListener implements LocationListener {



        @Override
        public void onLocationChanged(Location location) {
            location.setAccuracy(Criteria.ACCURACY_HIGH);

            lat = location.getLatitude();
            lon = location.getLongitude();

            Log.i("TAG", "Defaulter Service - location listener");
            Log.i("TAG","User location is "+lat + " " + lon + " ");

            origin = lat + "," + lon; //Location of user



            databaseReference.child("Bus app coordinates").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    CoordinatesInfo coordinates = dataSnapshot.getValue(CoordinatesInfo.class);
                    destination = Double.toString(coordinates.getLat())+","+Double.toString(coordinates.getLon()); //Location of bus
                    Log.i("TAG","Location of bus "+ destination);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            calculateDistance(origin, destination);
        }

        private void calculateDistance(String origin, String destination) {

            Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE__URL).addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            client = retrofit.create(MapInterface.class);

            Call<MapData> data = client.getMapData(origin, destination, "driving", API_KEY);
            Log.i("TAG","API CALL -" + origin + " "+ destination);
            data.enqueue(new Callback<MapData>() {
                @Override
                public void onResponse(Call<MapData> call, Response<MapData> response) {
                    MapData mapData = response.body();
                    Log.i("TAG",mapData.getStatus());

                    if(mapData.getStatus().equals("OK")) {
                    List<Row> rowData = mapData.getRows();
                    Row row = rowData.get(0);
                    List<Element> elementList = row.getElements();
                    Distance distObject = elementList.get(0).getDistance();
                    distance = distObject.getValue();
                    Log.i("TAG","Distance between user and QR "+ distance);

                        if(i<20) {

                            if(distance<500) {
                                checks[i].equals(false);
                            }

                            i++;
                        }

                        else {

                            int countDefaulters=0;
                            for(int j=0;j<20;j++) {
                                if(checks[j].equals(false)) {
                                    countDefaulters++;
                                }
                            }

                            if(countDefaulters>10) {
                                Log.i("TAG","Defaulter");
                            }

                            stopSelf();
                        }



                    }



                }

                @Override
                public void onFailure(Call<MapData> call, Throwable t) {

                }
            });

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
