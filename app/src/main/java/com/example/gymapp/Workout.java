package com.example.gymapp;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Entity(tableName = "Workout")
public class Workout {


    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "duration")
    int duration;
    @ColumnInfo(name = "description")
    String desc;
    @ColumnInfo(name = "exercises")
    HashMap<Exercises, List<Sets>> exercises;
    @ColumnInfo(name = "date")
    LocalDate date;

    @Ignore
    public Workout(){

    }

    public Workout(int duration, String desc, HashMap<Exercises, List<Sets>> exercises, LocalDate date) {
        this.duration = duration;
        this.desc = desc;
        this.exercises = exercises;
        this.date = date;
    }
    @Ignore
    public int getId() {
        return id;
    }
    @Ignore
    public void setId(int id) {
        this.id = id;
    }
    @Ignore
    public String getDesc() {
        return desc;
    }
    @Ignore
    public void setDesc(String desc) {
        this.desc = desc;
    }
    @Ignore
    public int getDuration() {
        return duration;
    }
    @Ignore
    public void setDuration(int duration) {
        this.duration = duration;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public HashMap<Exercises, List<Sets>> getExercises() {
        return exercises;
    }

    public void setExercises(HashMap<Exercises, List<Sets>> exercises) {
        this.exercises = exercises;
    }


}
