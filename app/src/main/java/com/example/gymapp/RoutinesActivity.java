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

public class RoutinesActivity extends AppCompatActivity implements RoutineListener {


    String splitName;
    List<Routines> dataList;
    RecyclerView recyclerView;
    RoutineAdapter adapter;
    Dialog dialog;
    MyDatabase db;
    Button save;

    ImageButton back,add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routines);


        db = MyDatabase.getINSTANCE(this.getApplicationContext());

        back = findViewById(R.id.routineBackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent splitIntent = getIntent();
        Bundle bundle = splitIntent.getExtras();
        splitName = bundle.getString("split");


        for (Split item : db.splitDao().getall()) {
            if(item.getName().equals(splitName)){
                if(item.getRoutinesList() == null){
                    dataList = new ArrayList<>();
                }else{
                dataList = item.getRoutinesList();
                break;
                }
            }
        }

        setUpRecyclerview();
        createDialog();

        save = dialog.findViewById(R.id.addNameButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewRoutine();
            }
        });

        add = findViewById(R.id.routineAddButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });





    }

    void createDialog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
    }

    public void addNewRoutine(){
        EditText text = dialog.findViewById(R.id.saveNameText);

        for (Routines item: dataList) {
            if(item.getName().equalsIgnoreCase(text.getText().toString())) {
                Toast.makeText(this, "Name already exists!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                text.setText("");
                return;
            }
        }
        Routines added = new Routines(text.getText().toString());
        dataList.add(added);
        db.splitDao().updateRoutines(dataList,splitName);
        adapter.notifyItemInserted(dataList.indexOf(added));
        text.setText("");
        dialog.dismiss();


    }

    public void setUpRecyclerview(){
    recyclerView = findViewById(R.id.routineRec);
    adapter = new RoutineAdapter(dataList,this,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void editButton(Routines routine) {
        Intent intent = new Intent(this, ExercisesActivity.class);
        //we put this data to the next activity for usage
        intent.putExtra("split", splitName);
        intent.putExtra("routine",routine.getName());
        startActivity(intent);




    }

    @Override
    public void deleteButton(Routines routine) {

        int deletedIndex = dataList.indexOf(routine);
        dataList.remove(routine);
        db.splitDao().updateRoutines(dataList,splitName);
        adapter.notifyItemRemoved(deletedIndex);
        Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show();

    }

}