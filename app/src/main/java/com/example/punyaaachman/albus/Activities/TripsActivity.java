package com.example.punyaaachman.albus.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.punyaaachman.albus.POJO.GlobalVariables;
import com.example.punyaaachman.albus.R;

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

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new TripsAdapter(GlobalVariables.profile.getTripsList());
        layoutManager  = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
}
