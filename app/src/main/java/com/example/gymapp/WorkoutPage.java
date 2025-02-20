package com.example.gymapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class WorkoutPage extends AppCompatActivity implements IWorkoutListener, ISetListener{

    private Button add, finish;
    private CountDownTimer timer;
    private int elapsedTime = 0;
    private TextView timerText;

    Boolean timerStarted = false;
    private Context context;
    private RecyclerView recyclerView, dialogRecyclerview;
    private WorkoutAdapter workoutAdapter;
    private HashMap<Exercises, List<Sets>> currentWorkout;
    private Routines routine;
    private MyDatabase db;
    private Dialog dialog;
    DialogAdapter dialogAdapter;

    ImageButton delete,back;
    TextView exerciseName;
    Button newSet;
    Exercises openedExercise;




    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        elapsedTime = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_page);
        db = MyDatabase.getINSTANCE(this);


        Bundle bundle = getIntent().getExtras();
        currentWorkout = new HashMap<>();

        if(!bundle.isEmpty()){
            routine =   Converters.fromStringToRoutine(bundle.getString("data")) ;

            for (Exercises item :routine.getListOfExercises()) {
                currentWorkout.put(item, new ArrayList<>());
            }
        }else{
            routine = new Routines("Empty");
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
                timerStarted = false;

                Workout save = new Workout(elapsedTime,routine.getName(),currentWorkout, LocalDate.now());

                db.workoutDao().insertWorkout(save);

                finish();
            }
        });

        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                timerStarted = false;
                finish();
            }
        });

        timer.start();

        initDialog();

        exerciseName= dialog.findViewById(R.id.exerciseName);
        dialogRecyclerview = dialog.findViewById(R.id.workouts);
        delete = dialog.findViewById(R.id.deleteExerciseButton);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexToDelete = indexOfExercise(openedExercise);

                currentWorkout.remove(openedExercise);
                workoutAdapter.notifyItemRemoved(indexToDelete);

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
                    dialogAdapter.notifyItemInserted(index-1);
                    }
                    else{
                        currentWorkout.get(openedExercise).add(new Sets(index,0,0));
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(context){
            @Override
            public boolean canScrollVertically(){
                return true;
            }
        };
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

    @Override
    protected void onStart() {
        super.onStart();

        if(timerStarted){
        SharedPreferences preferences = getSharedPreferences("time", MODE_PRIVATE);
            int elapsed = preferences.getInt("elapsedTime",0);
            Long timePassed = preferences.getLong("timePassed",0);

            elapsedTime = (int) (elapsed+ (System.currentTimeMillis()-timePassed));

            updateTimerText();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        if(timerStarted) {
            SharedPreferences preferences = getSharedPreferences("time", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putInt("elapsedTime", elapsedTime);
            editor.putLong("timePassed", System.currentTimeMillis());

            editor.apply();
        }
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
        for(int i = 0; i < currentWorkout.get(exercises).size();i++){
            currentWorkout.get(exercises).get(i).setSet(i+1);
        }
        dialogAdapter.notifyItemRemoved(sets.getSet()-1);
    }


    @Override
    public void openExercise(Exercises exercises) {
        dialogAnimation();
        dialog.show();
        openedExercise = exercises;
        exerciseName.setText(exercises.getName());
        initDialogRecyclerView(exercises);
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

            int indexChanged = indexOfExercise(openedExercise);
            workoutAdapter.notifyItemChanged(indexChanged);
            openedExercise = null;
        });
    }

}