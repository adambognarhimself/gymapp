package com.example.gymapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Exercises")
public class Exercises {



    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "name")
    private String name;


    public Exercises(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Ignore
    public Exercises(String name){
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Ignore
    public int getId() {
        return id;
    }
    @Ignore
    public void setId(int id) {
        this.id = id;
    }

}
