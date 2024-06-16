package com.example.gymapp.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.ChooseExercisesActivity;
import com.example.gymapp.Converters;
import com.example.gymapp.Exercises;
import com.example.gymapp.R;
import com.example.gymapp.Sets;
import com.example.gymapp.WorkoutAdapter;
import com.example.gymapp.WorkoutListener;
import com.example.gymapp.WorkoutRowsAdapter;
import com.example.gymapp.databinding.FragmentDashboardBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardFragment extends Fragment implements WorkoutListener {

    private FragmentDashboardBinding binding;
    ImageButton startStop, add;
    boolean timerStarted = false;
    CountDownTimer timer;
    long elapsedTime = 0;
    TextView timerText;

    Context context;
    RecyclerView exercise;
    RecyclerView sets;
    View root;
    WorkoutAdapter workoutAdapter;
    WorkoutRowsAdapter workoutRowsAdapter;


    private ActivityResultLauncher<Intent> activityResultLauncher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();

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
                        }
                    }
                });

        context = getContext();

        timerText = root.findViewById(R.id.durationText);

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

        startStop = root.findViewById(R.id.startStopButton);
        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!timerStarted){
                    timer.start();
                    timerStarted = true;
                    startStop.setImageResource(R.drawable.baseline_stop_circle_24);


                }
                else{
                timer.cancel();
                timerStarted = false;

                elapsedTime = 0;
                updateTimerText();
                    startStop.setImageResource(R.drawable.baseline_play_circle_24);


                }




        }});

        add = root.findViewById(R.id.exerciseAddButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChooseExercisesActivity.class);


                activityResultLauncher.launch(intent);
            }
        });

        setupRecyclerView();



        return root;
    }

    private void updateTimerText() {

        int seconds = (int) (elapsedTime / 1000) % 60;
        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
        int hours = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
        String timeString = String.format("%2d:%02d:%02d", hours, minutes, seconds);
        timerText.setText(timeString);
    }

    private void setupRecyclerView() {
        exercise = root.findViewById(R.id.workoutRecView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        exercise.setLayoutManager(layoutManager);



        HashMap<Exercises, List<Sets>> data = new HashMap<>();

        Exercises test = new Exercises("t1");
        Sets testSet = new Sets(1,30,5);
        List<Sets> testList = new ArrayList<>();
        testList.add(testSet);

        data.put(test,testList);

        List<Exercises> names = (List<Exercises>) data.keySet();
        List<List<Sets>> sets = new ArrayList<>();
        sets.add(testList);

        workoutAdapter = new WorkoutAdapter(names,context,this);
        exercise.setAdapter(workoutAdapter);






    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}