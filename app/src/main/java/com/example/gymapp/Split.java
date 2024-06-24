package com.example.gymapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Split")
public class Split {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "routines")
    private List<Routines> routinesList;
    @ColumnInfo(name ="displayed")
    private boolean displayed;



    @Ignore
    public Split(){

    }
    @Ignore
    public Split(int id, String name, List<Routines> routinesList,boolean displayed) {
        this.id = id;
        this.name = name;
        this.routinesList = routinesList;
        this.displayed = displayed;
    }

    public Split(String name, List<Routines> routinesList) {
        this.name = name;
        this.routinesList = routinesList;
        this.displayed = false;
    }
    @Ignore
    public Split(String name, List<Routines> routinesList,boolean displayed) {
        this.name = name;
        this.routinesList = routinesList;
        this.displayed = displayed;
    }



    @Ignore
    public Split(String name){
        this.name = name;
        this.displayed = false;
        this.routinesList = new ArrayList<>();
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

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

}
