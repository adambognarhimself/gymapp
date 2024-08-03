package com.example.gymapp;

public class DayModel {
    private String date;
    private String dayOfWeek;
    private boolean hasWorkout;

    public DayModel(String date, String dayOfWeek, boolean hasWorkout) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.hasWorkout = hasWorkout;
    }

    public String getDate() {
        return date;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean hasWorkout() {
        return hasWorkout;
    }
}
