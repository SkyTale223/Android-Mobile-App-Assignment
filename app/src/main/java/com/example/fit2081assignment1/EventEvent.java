package com.example.fit2081assignment1;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "eventEvent")
public class EventEvent {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private  int id;

    @ColumnInfo(name = "eventID")
    private String eventID;

    @ColumnInfo(name = "eventName")
    private String eventName;

    @ColumnInfo(name = "eventCategoryID")
    private String eventCategoryID;

    @ColumnInfo(name = "eventTickets")
    private int eventTickets;

    @ColumnInfo(name = "eventActive")
    private boolean eventActive;

    public String getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventCategoryID() {
        return eventCategoryID;
    }

    public int getEventTickets() {
        return eventTickets;
    }

    public boolean isEventActive() {
        return eventActive;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}


    public EventEvent(String eventID, String eventName, String eventCategoryID, int eventTickets, boolean eventActive) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventCategoryID = eventCategoryID;
        this.eventTickets = eventTickets;
        this.eventActive = eventActive;
    }
}
