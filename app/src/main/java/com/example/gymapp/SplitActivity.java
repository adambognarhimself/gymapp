package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SplitActivity extends AppCompatActivity implements SplitListener{

    RecyclerView recyclerView;
    ImageButton back, add;
    Button save;
    TextView pageName;

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
      createDialog();


      add = findViewById(R.id.splitAddButton);
      add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              dialog.show();
          }
      });

      save = dialog.findViewById(R.id.saveData);

      save.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            addNewSplit();
              recreate();
          }
      });

      pageName = dialog.findViewById(R.id.pageName);
      pageName.setText("New Split");


    }

    private void openRoutinesActivity(Split split) {
        Intent intent = new Intent(this, RoutinesActivity.class);
        intent.putExtra("split", split.getName());
        startActivity(intent);
    }

    void setUpRecyclerview()  {
        recyclerView = findViewById(R.id.splitRec);
        adapter = new SplitAdapter(data,this,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    void createDialog(){
        //The setup of the little popup window-
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

    }
    public void addNewSplit(){
        EditText text = dialog.findViewById(R.id.nameText);

        for (Split item: data) {
            if(item.getName().equalsIgnoreCase(text.getText().toString())) {
                //If it already exists in the db it will not be added
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
        openRoutinesActivity(split);
    }

    @Override
    public void deleteButton(Split split) {
        db.splitDao().deleteSplit(split);
        int index = data.indexOf(split);
        data.remove(split);
        adapter.notifyItemRemoved(index);
    }

    @Override
    public void selectButton(Split split) {
        //We select what will be shown on the main screen
        for (Split item:db.splitDao().getall()) {
            db.splitDao().updateDisplayed(item.getName().equals(split.getName()),item.getName());
        }
        finish();
    }
}

