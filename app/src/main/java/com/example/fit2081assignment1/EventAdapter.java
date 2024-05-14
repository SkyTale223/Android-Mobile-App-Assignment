package com.example.fit2081assignment1;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.eventViewHolder>{

    ArrayList<EventEvent> eventData = new ArrayList<EventEvent>();
    public void setData(ArrayList<EventEvent> data) {
        this.eventData = data;
    }

    @NonNull
    @Override
    public eventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventV = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list, parent, false); //CardView inflated as RecyclerView list item
        eventViewHolder viewHolder = new eventViewHolder(eventV);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull eventViewHolder holder, int position) {
        holder.tv_event_id.setText(eventData.get(position).getEventID());
        holder.tv_event_name.setText(eventData.get(position).getEventName());
        holder.tv_event_category_id.setText(eventData.get(position).getEventCategoryID());
        holder.tv_event_ticket.setText(String.valueOf(eventData.get(position).getEventTickets()));

        boolean isEventActive = eventData.get(position).isEventActive();

        String eventActive = isEventActive? "Active" : "Inactive";
        holder.tv_event_active.setText(eventActive);

        holder.itemView.setOnClickListener(v -> {
            String selectedEvent = eventData.get(position).getEventName();


            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, EventGoogleResult.class);
            intent.putExtra("SelectedEvent", selectedEvent);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount(){
        return eventData.size();
    }

    public class eventViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_event_id;
        public TextView tv_event_name;
        public TextView tv_event_category_id;
        public TextView tv_event_ticket;
        public TextView tv_event_active;

        public eventViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_event_id = itemView.findViewById(R.id.textViewEventID);
            tv_event_name = itemView.findViewById(R.id.textViewEventName);
            tv_event_category_id = itemView.findViewById(R.id.textViewEventCategoryID);
            tv_event_ticket = itemView.findViewById(R.id.textViewEventTickets);
            tv_event_active = itemView.findViewById(R.id.textViewEventActive);
        }

    }
}