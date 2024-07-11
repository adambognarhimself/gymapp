package com.example.gymapp;

public interface ISetListener {

    void setChanged(Exercises exercises, Sets set);

    void setDeleted(Exercises exercises, Sets sets);
}
