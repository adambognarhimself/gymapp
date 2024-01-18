package com.example.gymapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
@Dao
public interface RoutinesDao {
    @Insert
    void insertRoutine(Routines routines);


    @Update
    void updateRoutine(Routines routines);

    @Delete
    void deleteRoutine(Routines routines);
}
