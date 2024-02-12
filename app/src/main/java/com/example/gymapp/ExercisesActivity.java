package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
    Dialog dialog;
    MyDatabase db;
    Button save;



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
                dialog.show();
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
                        dataList = item2.getListOfExercises();
                        split = item;
                        routine = item2;
                        break;
                    }
                }
            }
        }

        setUpRecyclerview();
        createDialog();

        save = dialog.findViewById(R.id.addNameButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewExercise();
            }
        });

 }

    public void addNewExercise(){
        EditText text = dialog.findViewById(R.id.saveNameText);

        for (Exercises item: dataList) {
            if(item.getName().equalsIgnoreCase(text.getText().toString())) {
                Toast.makeText(this, "Name already exists!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                text.setText("");
                return;
            }
        }
        //We get the names of all exercises
        List<String> names = db.exercisesDao().getall().stream().map(x-> x.getName().toLowerCase()).collect(Collectors.toList());
        Exercises added = new Exercises(text.getText().toString());

        //we check if the database already has the new name
        if(!names.contains(text.toString().toLowerCase())){
            //if not then added
            db.exercisesDao().insertExercises(added);
        }
        dataList.add(added);

        updateDatabase();

        adapter.notifyItemInserted(dataList.indexOf(added));
        text.setText("");
        dialog.dismiss();

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

    void createDialog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
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