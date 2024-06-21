package com.example.gymapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.MyDatabase;
import com.example.gymapp.R;
import com.example.gymapp.RecyclerViewAdapter;
import com.example.gymapp.Split;
import com.example.gymapp.SplitActivity;
import com.example.gymapp.Workout;
import com.example.gymapp.databinding.FragmentHomeBinding;
import com.example.gymapp.ui.dashboard.DashboardFragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private RecyclerView recyclerView;
    private ArrayList<Workout> workoutList;

    Context context;

    MyDatabase db;

    Button getSplitButton;


    Button splitButton, nextButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getContext();


        db = MyDatabase.getINSTANCE(context);
        splitButton = root.findViewById(R.id.splitButton);
        nextButton = root.findViewById(R.id.nextButton);



        setCurrentSplit();



        recyclerView = root.findViewById(R.id.recView);
        workoutList = new ArrayList<>();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(workoutList,context);
        GridLayoutManager layoutManager = new GridLayoutManager(context,2);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);




        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSplitActivity();
            }
        });




        return root;
    }



    public void openSplitActivity(){
        Intent intent = new Intent(context, SplitActivity.class);

        startActivity(intent);

    }

    public String getCurrentSplit(){
        for (Split item:db.splitDao().getall()) {
            if(item.isDisplayed()){
                return item.getName();
            }
        }
        return "Unknown";
    }

    public void setCurrentSplit(){
        splitButton.setText("CURRENT SPLIT: " + getCurrentSplit());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}