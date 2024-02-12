package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChooseExercisesActivity extends AppCompatActivity {

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

        db = MyDatabase.getINSTANCE(context);
        context = this;

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


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO filtered fix with button functionality and visibility fix
                filtered.clear();
                for(int i = 0;i<data.size();i++){
                    //if the typed in text is contained in any exercises name it will be added to the filter and shown on screen
                    if(data.get(i).getName().toLowerCase().trim().contains(search.getText().toString().toLowerCase())){
                        String variable = search.getText().toString();
                        filtered.add(data.get(i));
                    }
                }

                adapter.notifyDataSetChanged();

                if(search.getText().toString().isEmpty()){
                    //Toast.makeText(context,"Empty",Toast.LENGTH_SHORT).show();
                    for (Exercises item: filtered) {
                        Toast.makeText(context,item.getName(),Toast.LENGTH_SHORT).show();
                    }
                    filtered.clear();

                    if(add.getVisibility() == View.VISIBLE){
                        add.setVisibility(View.INVISIBLE);
                    }
                }
                else{
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
        adapter = new ChooseAdapter(filtered,context);
        recyclerView.setAdapter(adapter);
    }
}