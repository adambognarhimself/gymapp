package com.example.gymapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "Exercises")
public class Exercises {



    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name ="date")
    private LocalDate date;


    public Exercises(int id, String name,LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    @Ignore
    public Exercises(String name,LocalDate date){
        this.name = name;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
