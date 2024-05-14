package com.example.fit2081assignment1.provider;

import android.app.Application;
import android.media.metrics.Event;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.fit2081assignment1.EventCategory;
import com.example.fit2081assignment1.EventEvent;
import com.example.fit2081assignment1.provider.EMADao;
import com.example.fit2081assignment1.provider.EMADatabase;

import java.util.List;

public class EMARepository {

    // private class variable to hold reference to DAO
    private EMADao emaDAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<EventCategory>> allEventCategoryLiveData;

    private LiveData<List<EventEvent>> allEventEventLiveData;

    // constructor to initialise the repository class
    EMARepository(Application application) {
        // get reference/instance of the database
        EMADatabase db = EMADatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        emaDAO = db.EMADao();

        // once the class is initialised get all the items in the form of LiveData
        allEventCategoryLiveData = emaDAO.getEventCategory();

        allEventEventLiveData = emaDAO.getEventEvent();
    }

    LiveData<List<EventCategory>> getAllEventCategory() {
        return allEventCategoryLiveData;
    }
    LiveData<List<EventEvent>> getAllEventEventLiveData() {
        return allEventEventLiveData;
    }



    void insert(EventCategory eventCategory) {
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.addEventCategory(eventCategory));
    }

    void insert(EventEvent eventEvent) {
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.addEventEvent(eventEvent));
    }


    void deleteAllEventCategories() {
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.deleteAllEventCategory());
    }

    void deleteAllEventEvents(){
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.deleteAllEventEvents());
    }
    public void updateEventCategory(EventCategory category) {
        Log.d("", "Updating EventCategory: " + category.getCategoryID());
        EMADatabase.databaseWriteExecutor.execute(() -> {
            emaDAO.updateEventCategory(category);
            Log.d("Repository", "EventCategory updated: " + category.getCategoryID());
        });
    }



}
