package com.example.gymapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class WorkoutPage extends AppCompatActivity implements IWorkoutListener, ISetListener{

    private Button add;
    private ImageButton finish, timerButton;
    private boolean timerStarted = false;
    private CountDownTimer timer;
    private long elapsedTime = 0;
    private TextView timerText;
    private Context context;
    private RecyclerView recyclerView, dialogRecyclerview;
    private WorkoutAdapter workoutAdapter;
    private HashMap<Exercises, List<Sets>> currentWorkout;
    private Routines routine;
    private MyDatabase db;
    private Dialog dialog;
    DialogAdapter dialogAdapter;

    ImageButton delete, collapse;
    TextView exerciseName;
    Button newSet;

    Exercises openedExercise;








    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_page);
        db = MyDatabase.getINSTANCE(this);

        Bundle bundle = getIntent().getExtras();
        routine =   Converters.fromStringToRoutine(bundle.getString("data")) ;

        currentWorkout = new HashMap<>();

        for (Exercises item :routine.getListOfExercises()) {
            currentWorkout.put(item, new ArrayList<>());
        }

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Handle the result
                        Intent data = result.getData();
                        if (data != null) {
                            String returnedData = data.getStringExtra("selected");
                            Exercises converted = Converters.fromStringToExercise(returnedData);
                            currentWorkout.put(converted, new ArrayList<>());
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

        add = findViewById(R.id.addExercise);

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

//                db.workoutDao().insertWorkout(newWorkout);

                finish();
            }
        });

        timer.start();
        initDialog();

        collapse = dialog.findViewById(R.id.collapseButton);
        exerciseName= dialog.findViewById(R.id.exerciseName);
        dialogRecyclerview = dialog.findViewById(R.id.setsView);

        collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openedExercise = null;
                dialog.dismiss();
            }
        });

        newSet = dialog.findViewById(R.id.newSetButton);
        newSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Sets> sets = currentWorkout.get(openedExercise);
                int index = sets.size()+1;
                if(openedExercise != null){
                    if(sets.size()>0){
                    currentWorkout.get(openedExercise).add(new Sets(index,sets.get(sets.size()-1).getKg(),0));
                    workoutAdapter.notifyItemInserted(indexOfExercise(openedExercise));
                    dialogAdapter.notifyItemInserted(index-1);
                    }
                    else{
                        currentWorkout.get(openedExercise).add(new Sets(index,0,0));
                        workoutAdapter.notifyItemInserted(indexOfExercise(openedExercise));
                        dialogAdapter.notifyItemInserted(index-1);
                    }
                }
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

        recyclerView = findViewById(R.id.workoutRecView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        workoutAdapter = new WorkoutAdapter(currentWorkout,context,getPreviousWorkout(routine.getName()),this);
        recyclerView.setAdapter(workoutAdapter);
    }

    private void initDialogRecyclerView(Exercises exercises){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        dialogRecyclerview.setLayoutManager(layoutManager);

        dialogAdapter = new DialogAdapter(currentWorkout.get(exercises),context,getPreviousWorkout(routine.getName()),exercises,this);
        dialogRecyclerview.setAdapter(dialogAdapter);
    }

    private Optional<Workout> getPreviousWorkout(String name){

        List<Workout> list = db.workoutDao().getall();

        list.sort((w1, w2) -> w2.getDate().compareTo(w1.getDate()));

        return list.stream().filter(x -> x.getDesc().equals(name)).findFirst();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView();
    }

    void initDialog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.exercise_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    }


    @Override
    public void setChanged(Exercises exercises, Sets set) {

        currentWorkout.get(exercises).get(set.getSet()-1).updateSet(set);

        int index = indexOfExercise(exercises);

        workoutAdapter.notifyItemChanged(index);
    }

    private int indexOfExercise(Exercises exercises) {
        int index = 0;

        for (Exercises item:currentWorkout.keySet()) {
            if(exercises.equals(item)) break;

            index++;
        }
        return index;
    }

    @Override
    public void setDeleted(Exercises exercises, Sets sets) {
        currentWorkout.get(exercises).remove(sets.getSet()-1);

        int index = indexOfExercise(exercises);

        workoutAdapter.notifyItemChanged(index);
    }


    @Override
    public void openExercise(Exercises exercises) {
        dialogAnimation();

        dialog.show();
        openedExercise = exercises;
        exerciseName.setText(exercises.getName());

        initDialogRecyclerView(exercises);
        dialogAdapter.notifyDataSetChanged();
    }

    private void dialogAnimation() {
        FrameLayout root = dialog.findViewById(android.R.id.content);
        Animation up = AnimationUtils.loadAnimation(this,R.anim.slideup);
        Animation down = AnimationUtils.loadAnimation(this,R.anim.slidedown);
        root.startAnimation(up);

        dialog.findViewById(R.id.collapseButton).setOnClickListener(view -> {
            // Apply slide-down animation before dismissing the dialog
            down.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // Do nothing
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Dismiss the dialog after the animation ends
                    dialog.dismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // Do nothing
                }
            });
            root.startAnimation(down);
        });
    }

}