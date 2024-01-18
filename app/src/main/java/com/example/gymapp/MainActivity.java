package com.example.gymapp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.gymapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<Workout> workoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        MyDatabase db = Room.databaseBuilder(getApplicationContext(),MyDatabase.class, "gym").build();


      recyclerView = findViewById(R.id.recView);

        workoutList = new ArrayList<>();

        workoutList.add(new Workout(10,"kecske",null,null));
        workoutList.add(new Workout(10,"pacos",null,null));
        workoutList.add(new Workout(10,"répa",null,null));
        workoutList.add(new Workout(10,"malac",null,null));workoutList.add(new Workout(10,"kecske",null,null));
        workoutList.add(new Workout(10,"pacos",null,null));
        workoutList.add(new Workout(10,"répa",null,null));
        workoutList.add(new Workout(10,"malac",null,null));workoutList.add(new Workout(10,"kecske",null,null));
        workoutList.add(new Workout(10,"pacos",null,null));
        workoutList.add(new Workout(10,"répa",null,null));
        workoutList.add(new Workout(10,"malac",null,null));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(workoutList,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(layoutManager);
       recyclerView.setAdapter(adapter);

    }

}