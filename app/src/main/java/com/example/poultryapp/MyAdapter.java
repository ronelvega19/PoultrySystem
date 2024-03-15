package com.example.poultryapp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    ArrayList<Logs> list;



    public MyAdapter(ArrayList<Logs> list) {
        this.list = list;

    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Logs log = list.get(position);
        holder.logs.setText(log.getLog());
        holder.times.setText(log.getTime());
        holder.dates.setText(log.getDate());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView dates, times, logs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dates = itemView.findViewById(R.id.myDate);
            times = itemView.findViewById(R.id.myTime);
            logs = itemView.findViewById(R.id.myLogs);


        }
    }

}
