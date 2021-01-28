package com.example.dam_exam_subject_003.util;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WebsiteDao {


    @Query("SELECT * from websites")
    List<Website> getAll();

    @Query("SELECT * from websites WHERE name LIKE :text")
    List<Website> filter(String text);

    @Insert
    long insert(Website website);
}
