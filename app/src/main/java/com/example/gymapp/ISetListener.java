package com.example.gymapp;

import java.util.Set;

public interface ISetListener {
    void removeSet(Exercises exercises, Sets sets);

    void saveKG(Exercises exercises, int setID, int value);
    void saveReps(Exercises exercises,int setID, int value);

}
