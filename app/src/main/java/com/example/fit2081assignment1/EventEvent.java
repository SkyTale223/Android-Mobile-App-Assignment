package com.example.fit2081assignment1;

public class EventEvent {
    private String eventID;
    private String eventName;
    private String eventCategoryID;
    private int eventTickets;
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

    public EventEvent(String eventID, String eventName, String eventCategoryID, int eventTickets, boolean eventActive) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventCategoryID = eventCategoryID;
        this.eventTickets = eventTickets;
        this.eventActive = eventActive;
    }
}
