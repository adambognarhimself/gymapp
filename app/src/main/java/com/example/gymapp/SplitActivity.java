package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SplitActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split);

        LinearLayout linearLayout = getLayoutInflater().inflate(R.layout.splitcard_layout,null).findViewById(R.id.splitLayout);

        ArrayList<Split> ls = new ArrayList<>();
        List<Routines> l2 = new ArrayList<>();
        List<Exercises> l3 = new ArrayList<>();
        List<Exercises> l4 = new ArrayList<>();

        l3.add(new Exercises("egy"));
        l4.add(new Exercises("2"));
        l3.add(new Exercises("3"));
        l3.add(new Exercises("4"));
        l4.add(new Exercises("5"));
        l3.add(new Exercises("6"));
        l3.add(new Exercises("7"));
        l4.add(new Exercises("8"));

        l2.add(new Routines("r1",l3));
        l2.add((new Routines("r2",l4)));
        ls.add(new Split("s1", l2));




        recyclerView = findViewById(R.id.splitRec);

       SplitAdapter adapter = new SplitAdapter(ls,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);

       recyclerView.setLayoutManager(layoutManager);
      recyclerView.setAdapter(adapter);

    }
}

