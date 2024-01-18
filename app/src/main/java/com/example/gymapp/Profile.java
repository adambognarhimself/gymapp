package com.example.gymapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Profile")
public class Profile{



    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "splits")
    List<Split> listOfSplits;
    @ColumnInfo(name = "workouts")
    List<Workout> workoutList;


    @Ignore
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Split> getListOfSplits() {
        return listOfSplits;
    }

    public void setListOfSplits(List<Split> listOfSplits) {
        this.listOfSplits = listOfSplits;
    }

    public List<Workout> getWorkoutList() {
        return workoutList;
    }

    public void setWorkoutList(List<Workout> workoutList) {
        this.workoutList = workoutList;
    }


}
