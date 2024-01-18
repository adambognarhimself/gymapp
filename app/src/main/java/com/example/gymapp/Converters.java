package com.example.gymapp;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    public static List<Workout> fromStringToWorkoutList(String value) {
        Type listType = new TypeToken<List<Workout>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromWourkoutListToString(List<Workout> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }




}
