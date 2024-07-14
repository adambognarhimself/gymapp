package com.example.gymapp;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Converters {

    private static final Gson gson = new Gson();

    @TypeConverter
    public static HashMap<Exercises, List<Sets>> fromStringToExercisesSetsMap(String value) {
        try {
            Type mapType = new TypeToken<HashMap<String, List<Sets>>>() {}.getType();
            HashMap<String, List<Sets>> tempMap = gson.fromJson(value, mapType);

            HashMap<Exercises, List<Sets>> finalMap = new HashMap<>();
            for (Map.Entry<String, List<Sets>> entry : tempMap.entrySet()) {
                finalMap.put(new Exercises(entry.getKey()), entry.getValue());
            }
            return finalMap;
        } catch (Exception e) {
            System.err.println("Failed to convert String to HashMap: " + value);
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static String fromExercisesSetsMapToString(HashMap<Exercises, List<Sets>> map) {
        HashMap<String, List<Sets>> tempMap = new HashMap<>();
        for (Map.Entry<Exercises, List<Sets>> entry : map.entrySet()) {
            tempMap.put(entry.getKey().getName(), entry.getValue());
        }
        return gson.toJson(tempMap);
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
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromSplitListToString(List<Split> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Exercises> fromStringToExerciseList(String value) {
        Type listType = new TypeToken<List<Exercises>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromExerciseListToString(List<Exercises> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static String fromExerciseToString(Exercises item) {
        return gson.toJson(item);
    }

    @TypeConverter
    public static Exercises fromStringToExercise(String value) {
        Type listType = new TypeToken<Exercises>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromRoutineToString(Routines item) {
        return gson.toJson(item);
    }

    @TypeConverter
    public static Routines fromStringToRoutine(String value) {
        Type listType = new TypeToken<Routines>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static List<Workout> fromStringToWorkoutList(String value) {
        Type listType = new TypeToken<List<Workout>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromWorkoutListToString(List<Workout> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static List<Routines> fromStringToRoutinesList(String value) {
        Type listType = new TypeToken<List<Routines>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromRoutinesListToString(List<Routines> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static Split fromStringToSplit(String value) {
        Type listType = new TypeToken<Split>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromSplitToString(Split split) {
        return gson.toJson(split);
    }
}
