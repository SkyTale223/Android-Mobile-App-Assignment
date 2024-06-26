package com.example.fit2081assignment1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.categoryViewHolder> {
    ArrayList<EventCategory> categoryData = new ArrayList<EventCategory>();

    public void setData(ArrayList<EventCategory> data) {
        this.categoryData = data;
    }

    @NonNull
    @Override
    public categoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Cardview inflated as a item of recyclerview
        View categoryV = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false);
        categoryViewHolder viewHolder = new categoryViewHolder(categoryV);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull categoryViewHolder holder, int position) {
        // Finally setting the views
        holder.tv_cat_id.setText(categoryData.get(position).getCategoryID());
        holder.tv_cat_name.setText(categoryData.get(position).getCategoryName());
        holder.tv_event_count.setText(String.valueOf(categoryData.get(position).getCategoryEventCount()));

        boolean isCategoryActive = categoryData.get(position).getCategoryActive();

        // Use ternary operator to convert into yes or no
        String categoryActive = isCategoryActive ? "Yes" : "No";
        holder.tv_cat_active.setText(categoryActive);

        // Find item that is clicked
        holder.itemView.setOnClickListener(v -> {
            String selectedLocation = categoryData.get(position).getCategoryLocation();
            String selectedCategoryName = categoryData.get(position).getCategoryName();

            // Pass it into Gogole Maps class as intent using key method
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, GoogleMapActivity.class);
            intent.putExtra("CategoryName", selectedCategoryName);
            intent.putExtra("Location", selectedLocation);
            // Start the new activity
            context.startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        // Return size of array list
        return categoryData.size();
    }

    public class categoryViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_cat_id;
        public TextView tv_cat_active;
        public TextView tv_cat_name;
        public TextView tv_event_count;

        public categoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_cat_id = itemView.findViewById(R.id.textViewCatID);
            tv_cat_name = itemView.findViewById(R.id.textViewCatName);
            tv_cat_active = itemView.findViewById(R.id.textViewCatActive);
            tv_event_count = itemView.findViewById(R.id.textViewEventCount);
        }
    }
}