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
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class WorkoutPage extends AppCompatActivity implements WorkoutListener{

    Button add;
    boolean timerStarted = false;
    CountDownTimer timer;
    long elapsedTime = 0;
    TextView timerText;

    Context context;
    RecyclerView exercise;
    WorkoutAdapter workoutAdapter;
    List<Exercises> currentWorkout;

    Routines routine;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_page);


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
        add = findViewById(R.id.addExerciseButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChooseExercisesActivity.class);


                activityResultLauncher.launch(intent);
            }
        });







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


        workoutAdapter = new WorkoutAdapter(routine.getName(),currentWorkout,context,this);
        exercise.setAdapter(workoutAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();
    }
}