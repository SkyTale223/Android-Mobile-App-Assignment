package com.example.fit2081assignment1.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fit2081assignment1.EventCategory;
import com.example.fit2081assignment1.EventEvent;

import java.util.List;


@Dao
public interface EMADao {

    @Query("select * from eventCategory")
    LiveData<List<EventCategory>> getEventCategory();

    @Insert
    void addEventCategory(EventCategory eventCategory);

    @Query("DELETE FROM eventCategory")
    void deleteAllEventCategory();

    @Update
    void updateEventCategory(EventCategory category);


    @Query("select * from eventEvent")
    LiveData<List<EventEvent>> getEventEvent();

    @Insert
    void addEventEvent(EventEvent eventEvent);

    @Query("DELETE FROM eventEvent")
    void deleteAllEventEvents();
}

