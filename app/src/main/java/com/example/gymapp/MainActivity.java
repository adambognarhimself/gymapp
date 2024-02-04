package com.example.gymapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<Workout> workoutList;

    MyDatabase db;

    Button getSplitButton;


    Button splitButton;

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
        //+
        // NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        db = MyDatabase.getINSTANCE(this.getApplicationContext());
        splitButton = findViewById(R.id.splitButton);



        setCurrentSplit();



      recyclerView = findViewById(R.id.recView);
    workoutList = new ArrayList<>();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(workoutList,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);

        recyclerView.setLayoutManager(layoutManager);
       recyclerView.setAdapter(adapter);




        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openSplitActivity();
            }
        });


    }

    public void openSplitActivity(){
        Intent intent = new Intent(this, SplitActivity.class);

        startActivity(intent);

    }

    public void setCurrentSplit(){
        for (Split item:db.splitDao().getall()) {
            if(item.isDisplayed()){
                splitButton.setText("CURRENT SPLIT: " + item.getName());
                break;
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setCurrentSplit();
    }
}
