package com.example.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.RecyclerViewHolder> {

    private List<Exercises>  courseDataArrayList;
    private Context mcontext;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private WorkoutListener workoutListener;

    MyDatabase db;
    List<Workout> workoutList;

    String name;


    public WorkoutAdapter(String name,List<Exercises> recyclerDataArrayList, Context mcontext, WorkoutListener workoutListener) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
        this.workoutListener = workoutListener;
        db = MyDatabase.getINSTANCE(mcontext);
        workoutList = db.workoutDao().getall();
        this.name = name;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_exercise_box, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview

        Exercises currentName = courseDataArrayList.get(position);

        holder.woExerciseName.setText(currentName.getName());

        List<Sets> sets = new ArrayList<>();
        if(getPreviousWorkout() != null){
            sets = getPreviousWorkout().getExercises().get(currentName);
        }



        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.child.getContext());
       layoutManager.setInitialPrefetchItemCount(sets.size());

       WorkoutRowsAdapter adapter = new WorkoutRowsAdapter(sets,mcontext);
        holder.child.setLayoutManager(layoutManager);
        holder.child.setAdapter(adapter);
       holder.child.setRecycledViewPool(viewPool);

    }

    public Workout getPreviousWorkout(){

        List<Workout> filteredWorkouts = new ArrayList<>();

        for (Workout item: workoutList) {
            if(item.getDesc().equals(name)){
                filteredWorkouts.add(item);
            }
        }

        if(filteredWorkouts.size() > 0)
            return filteredWorkouts.get(filteredWorkouts.size()-1);

        return null;

    }


    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }


    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView woExerciseName;

    private RecyclerView child;
        private Button addSet;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            woExerciseName = itemView.findViewById(R.id.wo_exercise_name);
            addSet = itemView.findViewById(R.id.wo_add_set);
            child = itemView.findViewById(R.id.workout_row_rec);
        }
    }
}
