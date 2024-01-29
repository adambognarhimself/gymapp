package com.example.gymapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SplitDao {

    @Insert
    void insertSplit(Split split);

    @Update
    void updateSplit(Split split);

    @Delete
    void deleteSplit(Split split);

    @Query("SELECT * FROM Split")
    List<Split> getall();


}
