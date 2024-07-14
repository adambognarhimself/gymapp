package com.example.gymapp.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.Converters;
import com.example.gymapp.MyDatabase;
import com.example.gymapp.OrderTouchHelper;
import com.example.gymapp.R;
import com.example.gymapp.RoutineOrderingAdapter;
import com.example.gymapp.Routines;
import com.example.gymapp.Split;
import com.example.gymapp.SplitActivity;
import com.example.gymapp.WorkoutPage;
import com.example.gymapp.databinding.FragmentDashboardBinding;

import java.util.List;

public class DashboardFragment extends Fragment{

     private FragmentDashboardBinding binding;


    Context context;

    MyDatabase db;



    Button splitButton, startNext, startEmpty;
    RecyclerView recyclerView;
    View root;
    RoutineOrderingAdapter routineOrderingAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
         root = binding.getRoot();
        context = getContext();
        db = MyDatabase.getINSTANCE(context);
        splitButton = root.findViewById(R.id.splitButton);


        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSplitActivity();
            }
        });

        startNext = root.findViewById(R.id.startNextButton);
        startNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWorkoutPage();
            }
        });

        startEmpty = root.findViewById(R.id.startEmptyButton);
        startEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmptyWorkoutPage();
            }
        });

        return root;
    }



    public void openSplitActivity(){
        Intent intent = new Intent(context, SplitActivity.class);
        startActivity(intent);
    }

    public Split getCurrentSplit(){
        for (Split item:db.splitDao().getall()) {
            if(item.isDisplayed()){
                return item;
            }
        }
        return new Split("Unknown");
    }

    public void setCurrentSplit(){
        splitButton.setText(getCurrentSplit().getName());
    }


    private void setupRecyclerView() {
        recyclerView = root.findViewById(R.id.routineView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        routineOrderingAdapter = new RoutineOrderingAdapter(getCurrentSplit(),context,db);
        ItemTouchHelper.Callback callback = new OrderTouchHelper(routineOrderingAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        routineOrderingAdapter.setItemTouchHelper(touchHelper);
        touchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(routineOrderingAdapter);
    }

    public void openWorkoutPage(){
        Intent intent = new Intent(context, WorkoutPage.class);
        Bundle bundle = new Bundle();

        if(!getCurrentSplit().getName().equals("Unknown")){
            Routines next = getCurrentSplit().getRoutinesList().get(0);
            bundle.putString("data",Converters.fromRoutineToString(next));


            //move first to last
            List<Routines> list = getCurrentSplit().getRoutinesList();
            list.remove(0);
            list.add(next);
            db.splitDao().updateRoutines(list,getCurrentSplit().getName());
        }

        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void openEmptyWorkoutPage(){
        Intent intent = new Intent(context,WorkoutPage.class);

        Bundle bundle = new Bundle();
        intent.putExtras(bundle);

        startActivity(intent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        setCurrentSplit();
        setupRecyclerView();
    }
}