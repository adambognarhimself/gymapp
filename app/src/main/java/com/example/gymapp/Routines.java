package com.example.gymapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

public class Routines {


    private String name;
    private List<Exercises> listOfExercises;

    public Routines(String name, List<Exercises> listOfExercises){
        this.name = name;
        this.listOfExercises = listOfExercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercises> getListOfExercises() {
        return listOfExercises;
    }

    public void setListOfExercises(List<Exercises> listOfExercises) {
        this.listOfExercises = listOfExercises;
    }

}
