package com.example.gymapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface SetsDao {
    @Insert
    void insertSet(Sets set);

    @Update
    void updateSet(Sets set);

    @Delete
    void deleteSet(Sets set);
}
