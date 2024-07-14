package com.example.gymapp;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class TypeAdapters {

    static class ExerciseAdapter extends TypeAdapter<Exercises>{

        @Override
        public void write(JsonWriter out, Exercises value) throws IOException {
            out.beginObject();
            out.name("id").value(value.getId());
            out.name("name").value(value.getName());
            out.endObject();
        }

        @Override
        public Exercises read(JsonReader in) throws IOException {
            in.beginObject();
            int id = 0;
            String name = null;
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "id":
                        id = in.nextInt();
                        break;
                    case "name":
                        name = in.nextString();
                        break;
                }
            }
            in.endObject();
            return new Exercises(id, name);
        }
    }

    static class SetsAdapter extends TypeAdapter<Sets> {
        @Override
        public void write(JsonWriter out, Sets value) throws IOException {
            out.beginObject();
            out.name("set").value(value.getSet());
            out.name("kg").value(value.getKg());
            out.name("reps").value(value.getReps());
            out.endObject();
        }

        @Override
        public Sets read(JsonReader in) throws IOException {
            in.beginObject();
            int set = 0;
            int kg = 0;
            int reps = 0;
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "set":
                        set = in.nextInt();
                        break;
                    case "kg":
                        kg = in.nextInt();
                        break;
                    case "reps":
                        reps = in.nextInt();
                        break;
                }
            }
            in.endObject();
            return new Sets(set, kg, reps);
        }
    }

}
