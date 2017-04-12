package com.example.punyaaachman.albus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SelectStationActivity extends AppCompatActivity {

    List<String> stops;
    RecyclerView recyclerView;
    BusStopAdapter busStopAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station);

        Intent intent = getIntent();
        String msg  = intent.getStringExtra("MSG");
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        stops = new ArrayList<>();
        int a = Integer.parseInt(msg);
        a++;
        switch (a){
            case 1: stops.add("District Centre, Janakpuir");
            case 2: stops.add("Peeragarhi");
            case 3: stops.add("Sachdeva");
            case 4:stops.add("DTU");

        }
       /* stops.add(0,"Rohini");
        stops.add(1,"Janakpuri");
        stops.add(2,"Bawana");
        stops.add(3,"Rajouri"); */

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        busStopAdapter = new BusStopAdapter(stops,this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(busStopAdapter);




    }
}
