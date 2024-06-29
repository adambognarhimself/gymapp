package com.example.gymapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkoutPage extends AppCompatActivity implements WorkoutListener, ISetListener{

    Button add, finish;
    boolean timerStarted = false;
    CountDownTimer timer;
    long elapsedTime = 0;
    TextView timerText;

    Context context;
    RecyclerView exercise;
    WorkoutAdapter workoutAdapter;
    List<Exercises> currentWorkout;

    Routines routine;

    MyDatabase db;

    List<Sets> setsList;

    Workout prev;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_page);
        db = MyDatabase.getINSTANCE(this);
        setsList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        routine =   Converters.fromStringToRoutine(bundle.getString("data")) ;

        currentWorkout = routine.getListOfExercises();

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Handle the result
                        Intent data = result.getData();
                        if (data != null) {
                            String returnedData = data.getStringExtra("selected");
                            Exercises converted = Converters.fromStringToExercise(returnedData);
                            Log.d("Returned Data", converted.getName());
                            currentWorkout.add(converted);
                            prev.getExercises().put(converted,new ArrayList<>());
                        }
                    }
                });

        context = this;

        timerText = findViewById(R.id.durationText);

        timer = new CountDownTimer(Long.MAX_VALUE,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                elapsedTime += 1000;
                updateTimerText();
            }

            @Override
            public void onFinish() {

            }
        };

//        startStop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(!timerStarted){
//                    timer.start();
//                    timerStarted = true;
//                    startStop.setImageResource(R.drawable.baseline_stop_circle_24);
//                }
//                else{
//                    timer.cancel();
//                    timerStarted = false;
//
//                    elapsedTime = 0;
//                    updateTimerText();
//                    startStop.setImageResource(R.drawable.baseline_play_circle_24);
//
//                }
//
//
//
//
//            }});

        add = findViewById(R.id.addExerciseButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChooseExercisesActivity.class);


                activityResultLauncher.launch(intent);
            }
        });

        finish = findViewById(R.id.finishButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                finish();
            }
        });

        timer.start();
        setPreviousWorkout();

    }


    private void updateTimerText() {

        int seconds = (int) (elapsedTime / 1000) % 60;
        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
        int hours = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
        String timeString = String.format("%2d:%02d:%02d", hours, minutes, seconds);
        timerText.setText(timeString);
    }

    private void setupRecyclerView() {



        exercise = findViewById(R.id.workoutRecView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        exercise.setLayoutManager(layoutManager);


        workoutAdapter = new WorkoutAdapter(currentWorkout,context,prev,this,this);
        exercise.setAdapter(workoutAdapter);
    }

    private void setPreviousWorkout() {
        if (getPreviousWorkout() != null){
            prev = getPreviousWorkout();
        }
        else{
          HashMap<Exercises,List<Sets>> exercisesListHashMap = new HashMap<>();

            for (Exercises item:routine.getListOfExercises()) {
                exercisesListHashMap.put(item,new ArrayList<>());
            }
         prev = new Workout(0,routine.getName(),exercisesListHashMap, LocalDate.now());
        }
    }

    public Workout getPreviousWorkout(){

        List<Workout> filteredWorkouts = new ArrayList<>();

        for (Workout item: db.workoutDao().getall()) {
            if(item.getDesc().equals(routine.getName())){
                filteredWorkouts.add(item);
            }
        }

        if(filteredWorkouts.size() > 0)
            return filteredWorkouts.get(filteredWorkouts.size()-1);

        return null;

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();
    }

    @Override
    public void addNewSet(Exercises exercises) {
        int index = prev.getExercises().get(exercises).size()+1;
        prev.getExercises().get(exercises).add(new Sets(index,0,0));
        workoutAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeExercise(Exercises exercises) {
        currentWorkout.remove(exercises);
        workoutAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeSet(Exercises exercises, Sets sets) {
        prev.getExercises().get(exercises).remove(sets);
        workoutAdapter.notifyDataSetChanged();
    }
}