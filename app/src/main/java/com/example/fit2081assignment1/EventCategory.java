package com.example.fit2081assignment1;

public class EventCategory {
        private String eventID;
        private String eventName;
        private String categoryID;
        private int tickets;

        public EventCategory(String eventID, String eventName, String categoryID, int tickets, boolean ticketsAvailable) {
                this.eventID = eventID;
                this.eventName = eventName;
                this.categoryID = categoryID;
                this.tickets = tickets;
                this.ticketsAvailable = ticketsAvailable;
        }

        public String getEventID() {
                return eventID;
        }

        public String getEventName() {
                return eventName;
        }

        public String getCategoryID() {
                return categoryID;
        }

        public int getTickets() {
                return tickets;
        }

        public boolean isTicketsAvailable() {
                return ticketsAvailable;
        }

        private boolean ticketsAvailable;
    }
