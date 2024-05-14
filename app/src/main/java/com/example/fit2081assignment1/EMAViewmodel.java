package com.example.fit2081assignment1;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EMAViewmodel extends AndroidViewModel {
    private EMARepository repository;
    private LiveData<List<EventCategory>> allEventCategoryLiveData;

    public EMAViewmodel(@NonNull Application application) {
        super(application);
        repository = new EMARepository(application);
        allEventCategoryLiveData = repository.getAllEventCategory();
    }

    public LiveData<List<EventCategory>> getAllEventCategoryLiveData() {
        return allEventCategoryLiveData;
    }

    public void insert(EventCategory eventCategory) {
        repository.insert(eventCategory);
    }

    public void deleteAll() {repository.deleteAll();};

    }
