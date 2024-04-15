package com.example.fit2081assignment1;

public class EventCategory {
    private String categoryID;
    private String categoryName;
    private int categoryEventCount;
    private boolean categoryActive;


    public String getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryEventCount() {
        return categoryEventCount;
    }

    public boolean getCategoryActive() {
        return categoryActive;
    }


    public EventCategory(String categoryID, String categoryName, int categoryEventCount, boolean categoryActive) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.categoryEventCount = categoryEventCount;
        this.categoryActive = categoryActive;
    }


    // Another setter method, in where the new integer will be updated by being replaced with count from dashboard
    public void setCategoryEventCount(int count) {
        this.categoryEventCount = count;
    }
}


















