package com.example.gymapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.InvalidationTracker;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.security.PrivateKey;
import java.util.List;

@Database(entities = {Split.class, Exercises.class, Workout.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MyDatabase extends RoomDatabase {
    public abstract ExercisesDao exercisesDao();
    public abstract WorkoutDao workoutDao();
    public  abstract SplitDao splitDao();

    private static MyDatabase INSTANCE;

    public static MyDatabase getINSTANCE(Context context){
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),MyDatabase.class,"gym.db").allowMainThreadQueries().build();
        }

        return INSTANCE;
    }




}




