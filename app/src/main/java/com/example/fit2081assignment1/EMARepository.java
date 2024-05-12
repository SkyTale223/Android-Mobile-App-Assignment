package com.example.fit2081assignment1;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.fit2081assignment1.provider.EMADao;
import com.example.fit2081assignment1.provider.EMADatabase;

import java.util.List;

public class EMARepository {

    // private class variable to hold reference to DAO
    private EMADao emaDAO;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<EventCategory>> allEventCategoryLiveData;

    // constructor to initialise the repository class
    EMARepository(Application application) {
        // get reference/instance of the database
        EMADatabase db = EMADatabase.getDatabase(application);

        // get reference to DAO, to perform CRUD operations
        emaDAO = db.EMADao();

        // once the class is initialised get all the items in the form of LiveData
        allEventCategoryLiveData = emaDAO.getEventCategory();
    }

    LiveData<List<EventCategory>> getAllEventCategory() {
        return allEventCategoryLiveData;
    }

    void insert(EventCategory eventCategory) {
        // Executes the database operation to insert the item in a background thread.
        EMADatabase.databaseWriteExecutor.execute(() -> emaDAO.addEventCategory(eventCategory));
    }
}
