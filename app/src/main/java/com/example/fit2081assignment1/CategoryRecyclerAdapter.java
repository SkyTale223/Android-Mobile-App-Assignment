package com.example.fit2081assignment1;

import android.media.metrics.Event;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CustomViewHolder>{
    // Creating an array list, based on what we defined in java/EventCategory
    ArrayList<EventCategory> data = new ArrayList<EventCategory>();

    // Setting the data to the array list
    public void setData(ArrayList<EventCategory> data) {
        this.data = data;
    }
    @NonNull
    @Override
    // Custom View Holder
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CardView inflated as RecycleView list item
        View categoryLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_dashboard_layout, parent, false);
        // Defining viewholder as layout from CustomViewHolder
        CustomViewHolder viewHolder = new CustomViewHolder(categoryLayout);
        // Return previously defined viewHolder
        return viewHolder;
    }

    @Override
    // Binding the data to the view holder
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvCategoryID.setText(String.valueOf(data.get(position).getCategoryID()));
        holder.tvCategoryName.setText(data.get(position).getCategoryName());
        holder.tvCategoryEventCount.setText(String.valueOf(data.get(position).getCategoryEventCount()));
        holder.tvCategoryActive.setText(String.valueOf(data.get(position).isCategoryActive()));

    }

    @Override
    // Getting the total item count
    public int getItemCount() {
        // If data is not equal then null
        if (this.data != null) {
            // Return the size of the array list
            return this.data.size();
        }
        // Return 0 if null
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        // Finding Views
        public TextView tvCategoryID;
        public TextView tvCategoryName;
        public TextView tvCategoryEventCount;
        public TextView tvCategoryActive;

        // Find items and view it
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
