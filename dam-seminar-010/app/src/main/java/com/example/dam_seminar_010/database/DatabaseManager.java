package com.example.dam_seminar_010.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.dam_seminar_010.database.dao.ExpenseDao;
import com.example.dam_seminar_010.database.model.Expense;
import com.example.dam_seminar_010.util.DateConverter;

@Database(entities = {Expense.class}, exportSchema = false, version = 1)
@TypeConverters({DateConverter.class})
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

    public abstract ExpenseDao getExpenseDao();
}
