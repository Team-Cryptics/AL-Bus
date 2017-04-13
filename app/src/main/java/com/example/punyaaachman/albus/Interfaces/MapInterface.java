package com.example.punyaaachman.albus.Interfaces;

import com.example.punyaaachman.albus.POJO_Map.MapData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by samarthgupta on 12/04/17.
 */

public interface MapInterface {


    @GET("/maps/api/distancematrix/json?")
    Call<MapData> getMapData(@Query("origins") String start, @Query("destinations") String end, @Query("mode") String mode, @Query("key") String apikey);


}
