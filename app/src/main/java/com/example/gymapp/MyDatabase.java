package com.example.gymapp;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.InvalidationTracker;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.List;

@Database(entities = {Split.class, Exercises.class, Workout.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MyDatabase extends RoomDatabase {
    public abstract ExercisesDao exercisesDao();
    public abstract WorkoutDao workoutDao();
    public  abstract SplitDao splitDao();




}




