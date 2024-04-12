package com.example.fit2081assignment1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.categoryViewHolder> {
    ArrayList<EventCategory> data = new ArrayList<EventCategory>();

    public void setData(ArrayList<EventCategory> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View categoryV = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false); //CardView inflated as RecyclerView list item
        categoryViewHolder viewHolder = new categoryViewHolder(categoryV);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {
        // Get ID and Name
        holder.tv_id.setText(data.get(position).getCategoryID());
        holder.tv_name.setText(data.get(position).getCategoryName());

        // Get Event Count and boolean and convert to string for TextView
        holder.tv_event_count.setText(String.valueOf(data.get(position).getCategoryEventCount()));
        holder.tv_active.setText(String.valueOf(data.get(position).getCategoryActive()));

        /* Debugging
        Log.d("CategoryAdapter", "ID: " + data.get(position).getCategoryID());
        Log.d("CategoryAdapter", "Name: " + data.get(position).getCategoryName());
        Log.d("CategoryAdapter", "Event Count: " + data.get(position).getCategoryEventCount());
        Log.d("CategoryAdapter", "Is Active: " + data.get(position).getCategoryActive());
         */

    }


    @Override
    public int getItemCount() {
        // Return size of array list
        return data.size();
    }

    public class categoryViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_id;
        public TextView tv_active;
        public TextView tv_name;
        public TextView tv_event_count;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);

            // Finally setting the views
            tv_id = itemView.findViewById(R.id.textViewCatID);
            tv_name = itemView.findViewById(R.id.textViewCatName);
            tv_active = itemView.findViewById(R.id.textViewCatActive);
            tv_event_count = itemView.findViewById(R.id.textViewEventCount);
        }
    }
}