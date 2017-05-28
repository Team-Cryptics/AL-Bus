package com.example.punyaaachman.albus.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.punyaaachman.albus.POJO.GlobalVariables;
import com.example.punyaaachman.albus.POJO.Trips;
import com.example.punyaaachman.albus.R;

import java.util.ArrayList;
import java.util.List;

public class TripsActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        ArrayList<Trips> trips = new ArrayList<>();
        if(GlobalVariables.trip!=null) {
        trips.add(GlobalVariables.trip); }
        Trips trip = new Trips("Janakpuri","Sachdeva Public School",20.0);
        trips.add(trip);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new TripsAdapter(trips);
        layoutManager  = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
}
