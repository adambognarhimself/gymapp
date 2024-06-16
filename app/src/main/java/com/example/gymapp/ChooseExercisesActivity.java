package com.example.gymapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChooseExercisesActivity extends AppCompatActivity implements ChooseListener{

    ImageButton back;

    EditText search;
    RecyclerView recyclerView;
    ChooseAdapter adapter;
    MyDatabase db;

    Context context;

    List<Exercises> data;
    List<Exercises> filtered;

    Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercises);

        context = this;
        db = MyDatabase.getINSTANCE(context);


        back = findViewById(R.id.chooseBackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        filtered = new ArrayList<>();

        setupRecyclerView();

        data = db.exercisesDao().getall();

        search = findViewById(R.id.searchText);

        add = findViewById(R.id.addButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButton(search.getText().toString());
            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                filtered.clear();
                for(int i = 0;i<data.size();i++){
                    //if the typed in text is contained in any exercises name it will be added to the filter and shown on screen
                    if(data.get(i).getName().toLowerCase().trim().contains(search.getText().toString().toLowerCase()) && !search.getText().toString().equalsIgnoreCase("")){
                        String variable = search.getText().toString();
                        filtered.add(data.get(i));
                    }
                }

                adapter.notifyDataSetChanged();

                if(filtered.isEmpty()){
                    if(search.getText().toString().isEmpty() || search.getText().toString().equalsIgnoreCase("")){
                        add.setVisibility(View.INVISIBLE);
                    }else{
                        add.setVisibility(View.VISIBLE);
                    }
                }else{
                    add.setVisibility(View.INVISIBLE);
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChooseAdapter(filtered,context,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void selectButton(Exercises exercise) {

        Intent intent = getIntent();
        intent.putExtra("selected",Converters.fromExerciseToString(exercise));
        setResult(RESULT_OK,intent);
        finish();

    }


    public void addButton(String name) {
        Exercises newExercise = new Exercises(name);
        db.exercisesDao().insertExercises(newExercise);

        Intent intent = getIntent();
        intent.putExtra("selected",Converters.fromExerciseToString(newExercise));
        setResult(RESULT_OK,intent);
        finish();

    }
}