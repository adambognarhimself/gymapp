package com.example.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.RecyclerViewHolder>{

    private HashMap<Exercises, List<Sets>> courseDataArrayList;
    private final Context mcontext;
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    private WorkoutRowsAdapter adapter;

    Workout previous;
    IWorkoutListener workoutListener;


    public WorkoutAdapter(HashMap<Exercises, List<Sets>> recyclerDataArrayList, Context mcontext, Optional<Workout> previousWorkout, IWorkoutListener workoutListener) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;

        previous = previousWorkout.orElse(null);

        this.workoutListener = workoutListener;
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

        List<Exercises> exercises = new ArrayList<>(courseDataArrayList.keySet());

        Exercises currentExercise = exercises.get(position);

        holder.woExerciseName.setText(currentExercise.getName());
        holder.openup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutListener.openExercise(currentExercise);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.child.getContext());

        List<Sets> previousSets = new ArrayList<>();
        if(previous != null){
            for (Exercises item:previous.getExercises().keySet()) {
                if(item.getName().equals(currentExercise.getName())){
                    previousSets = previous.getExercises().get(item);
                    adapter = new WorkoutRowsAdapter(courseDataArrayList.get(currentExercise),previousSets, mcontext);
                    break;
                }
            }
        }else{
            adapter = new WorkoutRowsAdapter(courseDataArrayList.get(currentExercise), mcontext);
        }


       holder.child.setLayoutManager(layoutManager);
       holder.child.setAdapter(adapter);
      holder.child.setRecycledViewPool(viewPool);

      if(courseDataArrayList.get(currentExercise).size()>0){
          holder.recyclerView.setVisibility(View.VISIBLE);
          holder.linearLayout.setVisibility(View.VISIBLE);
      }else{
          holder.recyclerView.setVisibility(View.GONE);
          holder.linearLayout.setVisibility(View.GONE);
      }

    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }




    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private final TextView woExerciseName;
    private final RecyclerView child;
    final private ImageButton openup;
    LinearLayout linearLayout;
     RecyclerView recyclerView;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            woExerciseName = itemView.findViewById(R.id.wo_exercise_name);
            child = itemView.findViewById(R.id.workout_row_rec);
            openup = itemView.findViewById(R.id.openExercise);
            linearLayout = itemView.findViewById(R.id.exerciseRow);
            recyclerView = itemView.findViewById(R.id.workout_row_rec);

        }
    }
}
