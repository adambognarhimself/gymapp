package com.example.gymapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface SplitDao {

    @Insert
    void insertSplit(Split split);

    @Update
    void updateSplit(Split split);

    @Delete
    void deleteSplit(Split split);
}
