package com.example.gymapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

public class Split {
    private String name;
    private List<Routines> routinesList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Routines> getRoutinesList() {
        return routinesList;
    }

    public void setRoutinesList(List<Routines> routinesList) {
        this.routinesList = routinesList;
    }
}
