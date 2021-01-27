package com.example.dam_seminar_010.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dam_seminar_010.database.model.Expense;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Query("SELECT * from expenses")
    List<Expense> getAll();

    @Insert
    long insert(Expense expense);

    @Update
    int update(Expense expense);

    @Delete
    int delete(Expense expense);
}
