package com.example.fit2081assignment1.provider;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fit2081assignment1.EventCategory;
import com.example.fit2081assignment1.EventEvent;

import java.util.List;

public class EMAViewmodel extends AndroidViewModel {
    private EMARepository repository;
    private LiveData<List<EventCategory>> allEventCategoryLiveData;

    private LiveData<List<EventEvent>> allEventEventLiveData;

    public EMAViewmodel(@NonNull Application application) {
        super(application);
        repository = new EMARepository(application);
        allEventCategoryLiveData = repository.getAllEventCategory();
        allEventEventLiveData = repository.getAllEventEventLiveData();
    }

    public LiveData<List<EventCategory>> getAllEventCategoryLiveData() {
        return allEventCategoryLiveData;
    }

    public LiveData<List<EventEvent>> getAllEventEventLiveData() {
        return allEventEventLiveData;
    }

    public void insert(EventCategory eventCategory) {
        repository.insert(eventCategory);
    }

    public void insert(EventEvent eventEvent){
        repository.insert(eventEvent);
    }

    public void deleteAllEventCategories() {repository.deleteAllEventCategories();}

    public void deleteAllEventEvents() {repository.deleteAllEventEvents();}
    public void updateEventCategory(EventCategory category) {
        Log.d("ViewModel", "Updating EventCategory: " + category.getCategoryID());
        repository.updateEventCategory(category);
    }

}
