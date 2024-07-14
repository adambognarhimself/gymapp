package com.example.gymapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExercisesActivity extends AppCompatActivity implements ExerciseListener {

    ImageButton back,add;
    String splitName,routineName;
    List<Exercises> dataList;
    Split split;
    Routines routine;

    RecyclerView recyclerView;
    ExerciseAdapter adapter;
    MyDatabase db;
    TextView pageName;

    private ActivityResultLauncher<Intent> activityResultLauncher;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);



        back = findViewById(R.id.exeBackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add = findViewById(R.id.exeAddButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChooseActivity();
            }
        });

        db = MyDatabase.getINSTANCE(this.getApplicationContext());

        Intent getIntent = getIntent();
        Bundle bundle = getIntent.getExtras();
        splitName = bundle.getString("split");
        routineName = bundle.getString("routine");


        //We get the correct items from the database
        for (Split item: db.splitDao().getall()) {
            if(item.getName().equals(splitName)){
                for (Routines item2: item.getRoutinesList()) {
                    if (item2.getName().equals(routineName)){
                        if(item2.getListOfExercises() == null){
                            dataList = new ArrayList<>();
                        }else{
                            dataList = item2.getListOfExercises();

                        }
                        split = item;
                        routine = item2;
                        break;
                    }
                }
            }
        }

        setUpRecyclerview();

        addNewExercise();

 }

 public void openChooseActivity(){
        Intent intent = new Intent(this, ChooseExercisesActivity.class);
        activityResultLauncher.launch(intent);
 }

    public void addNewExercise(){
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Handle the result
                        Intent data = result.getData();
                        if (data != null) {
                            String returnedData = data.getStringExtra("selected");
                            Exercises converted = Converters.fromStringToExercise(returnedData);

                            if(dataList.contains(converted)){
                                Toast.makeText(this, "Already exists!", Toast.LENGTH_SHORT).show();
                            }else{
                                dataList.add(converted);

                                updateDatabase();

                                adapter.notifyItemInserted(dataList.indexOf(converted));
                            }
                        }
                    }
                });


    }

    private void updateDatabase() {
        List<Routines> routines = new ArrayList<>();

        for (Routines item: split.getRoutinesList()) {
            //if the current routine name doesnt equal
            if(!item.getName().equals(routine.getName())){
                //we add it to the list
                routines.add(item);
            }else{
                //if it equals we update the routine with the new exercises then add it to the list
                item.setListOfExercises(dataList);
                routines.add(item);
            }
        }
        //updates routines
        db.splitDao().updateRoutines(routines,splitName);
    }

    public void setUpRecyclerview(){
        recyclerView = findViewById(R.id.exeRec);
        adapter = new ExerciseAdapter(dataList,this,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void deleteButton(Exercises exercise) {
        int deletedIndex = dataList.indexOf(exercise);
        dataList.remove(exercise);
        updateDatabase();
        adapter.notifyItemRemoved(deletedIndex);


    }
}