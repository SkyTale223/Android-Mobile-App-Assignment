package com.example.fit2081assignment1;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EMAViewmodel extends AndroidViewModel {
    // reference to CardRepository
    private EMARepository repository;
    // private class variable to temporary hold all the items retrieved and pass outside of this class
    private LiveData<List<EventCategory>> allEventCategoryLiveData;

    public EMAViewmodel(@NonNull Application application) {
        super(application);

        // get reference to the repository class
        repository = new EMARepository(application);

        // get all items by calling method defined in repository class
        allEventCategoryLiveData = repository.getAllEventCategory();
    }

    public LiveData<List<EventCategory>> getAllEventCategoryLiveData() {
        return allEventCategoryLiveData;
    }


    public void insert(EventCategory eventCategory) {
        repository.insert(eventCategory);
    }

}
