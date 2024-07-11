package com.example.gymapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Sets {



    private int set;
    private int kg;
    private int reps;

    @Ignore
    public Sets(int set, int kg, int reps) {
        this.set = set;
        this.kg = kg;
        this.reps = reps;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getKg() {
        return kg;
    }

    public void setKg(int kg) {
        this.kg = kg;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }
    public void updateSet(Sets set){
        this.reps = set.getReps();
        this.kg = set.getKg();
    }


}
