package com.example.fit2081assignment1.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fit2081assignment1.EventCategory;

import java.util.List;


@Dao
public interface EMADao {

    @Query("select * from eventCategory")
    LiveData<List<EventCategory>> getEventCategory();

    @Insert
    void addEventCategory(EventCategory eventCategory);
}

