package com.example.punyaaachman.albus.Activities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.punyaaachman.albus.POJO.Trips;
import com.example.punyaaachman.albus.R;

import java.util.ArrayList;

/**
 * Created by SUPERUSER on 27-05-2017.
 */

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripsViewHolder> {

    ArrayList<Trips> tripsArrayList = new ArrayList<Trips>();

    public TripsAdapter(ArrayList<Trips> tripsArrayList)
    {
        this.tripsArrayList= tripsArrayList;
    }

    @Override
    public TripsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trips_recycler,parent,false);
        TripsViewHolder holder = new TripsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TripsViewHolder holder, int position) {
       holder.tv1.setText(tripsArrayList.get(position).getStart());
        holder.tv2.setText(tripsArrayList.get(position).getEnd());

    }

    @Override
    public int getItemCount() {
        return tripsArrayList.size();
    }

    public static class TripsViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv1,tv2;
        public TripsViewHolder(View view)
        {
            super(view);
            tv1 = (TextView)view.findViewById(R.id.tvFrom);
            tv2 = (TextView)view.findViewById(R.id.tvTo);

        }
    }

}
