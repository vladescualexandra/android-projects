package com.example.dam_exam_subject_003.util;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Website.class}, exportSchema = false, version = 1)
public abstract class DatabaseManager extends RoomDatabase {

    private static final String DATABASE_NAME = "expenses_db";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(
                            context, DatabaseManager.class, DATABASE_NAME
                    ).fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return databaseManager;
    }

    public abstract WebsiteDao getWebsiteDao();
}
