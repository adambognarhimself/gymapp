package com.example.gymapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ExercisesDao {

    @Insert
    void insertExercises(Exercises exercises);


    @Update
    void updateExercises(Exercises exercises);

    @Delete
    void deleteExercises(Exercises exercises);
}
