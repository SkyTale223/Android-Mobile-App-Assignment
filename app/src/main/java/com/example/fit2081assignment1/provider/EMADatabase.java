package com.example.fit2081assignment1.provider;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fit2081assignment1.EventCategory;
import com.example.fit2081assignment1.EventEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {EventCategory.class, EventEvent.class}, version = 1)
public abstract class EMADatabase extends RoomDatabase{
    public static final String EMA_DATABASE = "ema_database";
    private static volatile EMADatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static EMADatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EMADatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EMADatabase.class, EMA_DATABASE).build();
                }
            }
        }
        return INSTANCE;
    }
}