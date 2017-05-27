package com.example.punyaaachman.albus.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.punyaaachman.albus.POJO.GlobalVariables;
import com.example.punyaaachman.albus.R;

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
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();    // gives the beg station number
        stops = new ArrayList<>();

        //DO NOT SCAN RANDOM BARCODES WHICH CANT BE PARSED
        int a = Integer.parseInt(msg);            //Integer station number
        GlobalVariables.begin = a;

        switch (a){
            case 0: GlobalVariables.b="Janakpuri";
                break;
            case 1: GlobalVariables.b="District Centre, Janakpuri";
                break;
            case 2: GlobalVariables.b="Peeragarhi";
                break;
            case 3: GlobalVariables.b="Sachdeva School,Rohini";
                break;
            case 4: GlobalVariables.b="DTU, Bawana Road";
                break;

        }
        a++;
        switch (a){
            case 1: stops.add("District Centre, Janakpuri");
            case 2: stops.add("Peeragarhi");
            case 3: stops.add("Sachdeva School,Rohini");
            case 4: stops.add("DTU, Bawana Road");

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
    public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.BusStopHolder> {

        List<String> stops= new ArrayList<>();
        Context c;

        public BusStopAdapter(List<String> stops, Context context)
        {
            this.stops = stops;
            c= context;
        }

        @Override
        public BusStopHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
            BusStopHolder holder = new BusStopHolder(view,stops);
            return holder;
        }

        @Override
        public void onBindViewHolder(BusStopHolder holder, int position) {

            holder.stopName.setText(stops.get(position));
        }

        @Override
        public int getItemCount() {
            return stops.size();
        }


        public class BusStopHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView stopName;
            RadioButton stopSelect;
            List<String> stops;
            AlertDialog alertDialog;
            String stopNameClicked;

            public BusStopHolder(View itemView, List<String> busStops) {
                super(itemView);
                itemView.setOnClickListener(this);
                stops=busStops;
                stopName = (TextView)itemView.findViewById(R.id.tv_name);
                stopSelect = (RadioButton)itemView.findViewById(R.id.tv_select);
            }

            @Override
            public void onClick(View view) {

                int position = getAdapterPosition();
                Log.i("TAG","CLICK on position "+position);
                Log.i("TAG",stops.get(position));
                stopNameClicked = stops.get(position);
                stopSelect.setChecked(true);
                setAlertDialog();

            }

            void setAlertDialog() {

                alertDialog = new AlertDialog.Builder(c).create();
                alertDialog.setTitle("Select Destination");
                alertDialog.setMessage("Select "+ stopNameClicked +" as destination?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "PROCEED",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                switch (stopNameClicked) {
                                    case "District Centre, Janakpuri": GlobalVariables.dest=1;
                                        GlobalVariables.d= "District Centre, Janakpuri";
                                        break;
                                    case "Peeragarhi": GlobalVariables.dest=2;
                                        GlobalVariables.d= "Peeragarhi";
                                        break;
                                    case "Sachdeva School,Rohini": GlobalVariables.dest=3;
                                        GlobalVariables.d= "Sachdeva School,Rohini";
                                        break;
                                    case "DTU, Bawana Road": GlobalVariables.dest=4;
                                        GlobalVariables.d= "DTU, Bawana Road";
                                        break;
                                }
                                //SEND INTENT
                                startActivity(new Intent(SelectStationActivity.this, PayActivity.class));
                                finish();
                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                stopSelect.setChecked(false);
                            }
                        });

                alertDialog.show();

            }
        }
    }
}
