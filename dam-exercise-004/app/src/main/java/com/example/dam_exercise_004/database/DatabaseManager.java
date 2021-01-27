package com.example.dam_exercise_004.database;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dam_exercise_004.database.dao.BusRideDao;
import com.example.dam_exercise_004.database.model.BusRide;


@Database(entities={BusRide.class}, exportSchema=false, version=1)
public abstract class DatabaseManager extends RoomDatabase {

    private static final String DATABASE_NAME = "bus_rides_db";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context,
                            DatabaseManager.class,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return  databaseManager;
    }

    public abstract BusRideDao getBusRideDao();
}