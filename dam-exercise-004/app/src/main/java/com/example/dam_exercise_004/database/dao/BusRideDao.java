package com.example.dam_exercise_004.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dam_exercise_004.database.model.BusRide;

import java.util.List;

@Dao
public interface BusRideDao {

    @Query("SELECT * from rides")
    List<BusRide> getAll();

    @Insert
    long insert(BusRide ride);

}
