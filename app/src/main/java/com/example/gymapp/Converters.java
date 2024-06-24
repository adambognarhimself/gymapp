package com.example.gymapp;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class Converters {

    @TypeConverter
    public static HashMap<Exercises, List<Sets>> fromStringToExercisesSetsMap(String value) {
        Type mapType = new TypeToken<HashMap<Exercises, List<Sets>>>() {}.getType();
        return new Gson().fromJson(value, mapType);
    }

    @TypeConverter
    public static String fromExercisesSetsMapToString(HashMap<Exercises, List<Sets>> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    @TypeConverter
    public static LocalDate fromStringToLocalDate(String value) {
        return LocalDate.parse(value);
    }

    @TypeConverter
    public static String fromLocalDateToString(LocalDate date) {
        return date.toString();
    }

    @TypeConverter
    public static List<Split> fromStringToSplitList(String value) {
        Type listType = new TypeToken<List<Split>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }



    @TypeConverter
    public static String fromSplitListToString(List<Split> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Exercises> fromStringToExerciseList(String value) {
        Type listType = new TypeToken<List<Exercises>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }



    @TypeConverter
    public static String fromExerciseListToString(List<Exercises> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static String fromExerciseToString(Exercises item) {
        Gson gson = new Gson();
        return gson.toJson(item);
    }

    @TypeConverter
    public static Exercises fromStringToExercise(String value) {
        Type listType = new TypeToken<Exercises>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromRoutineToString(Routines item) {
        Gson gson = new Gson();
        return gson.toJson(item);
    }

    @TypeConverter
    public static Routines fromStringToRoutine(String value) {
        Type listType = new TypeToken<Routines>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static List<Workout> fromStringToWorkoutList(String value) {
        Type listType = new TypeToken<List<Workout>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromWourkoutListToString(List<Workout> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Routines> fromStringToRoutinesList(String value) {
        Type listType = new TypeToken<List<Routines>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromRoutinesListToString(List<Routines> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static Split fromStringToSplit(String value) {
        Type listType = new TypeToken<Split>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromSplitToString(Split split) {
        Gson gson = new Gson();
        return gson.toJson(split);
    }





}
