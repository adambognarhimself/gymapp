package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SplitActivity extends AppCompatActivity implements SplitListener{

    RecyclerView recyclerView;
    ImageButton back, add;
    Button save;

    Dialog dialog;
    SplitAdapter adapter;

    MyDatabase db;

    List<Split> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);

        db = MyDatabase.getINSTANCE(this.getApplicationContext());
        data = db.splitDao().getall();

       back = findViewById(R.id.splitBackButton);

      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              finish();
          }
      });

    setUpRecyclerview();
      showDialog();


      add = findViewById(R.id.splitAddButton);
      add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dialog.show();
          }
      });

      save = dialog.findViewById(R.id.addNameButton);

      save.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            addNewSplit();
              recreate();
          }
      });


    }

    private void openRoutinesActivity() {
    }

    void setUpRecyclerview()  {
        recyclerView = findViewById(R.id.splitRec);
        adapter = new SplitAdapter(data,this,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    void showDialog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
    }
    public void addNewSplit(){
        EditText text = dialog.findViewById(R.id.saveNameText);

        for (Split item: data) {
            if(item.getName().equals(text.getText().toString())) {
                Toast.makeText(this, "Name already exists!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                text.setText("");
                return;
            }
        }
        Split added = new Split(text.getText().toString());
        data.add(added);
        db.splitDao().insertSplit(added);
        dialog.dismiss();
        adapter.notifyItemInserted(data.indexOf(added));
        text.setText("");

    }

    @Override
    public void editButton(Split split) {
        Toast.makeText(this,"Clicked position: " + data.indexOf(split),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteButton(Split split) {
        Toast.makeText(this,"Pressed delete",Toast.LENGTH_SHORT).show();
        db.splitDao().deleteSplit(split);
        int index = data.indexOf(split);
        data.remove(split);
        adapter.notifyItemRemoved(index);

    }

    @Override
    public void selectButton(Split split) {


        finish();
    }
}

