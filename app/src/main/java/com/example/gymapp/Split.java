package com.example.gymapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "Split")
public class Split {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "name")
    private String name;



    @ColumnInfo(name = "routines")
    private List<Routines> routinesList;




    @Ignore
    public Split(){

    }
    public Split(int id, String name, List<Routines> routinesList) {
        this.id = id;
        this.name = name;
        this.routinesList = routinesList;
    }
    @Ignore
    public Split(String name, List<Routines> routinesList) {
        this.name = name;
        this.routinesList = routinesList;
    }

    @Ignore
    public Split(String name){
        this.name = name;
    }

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
