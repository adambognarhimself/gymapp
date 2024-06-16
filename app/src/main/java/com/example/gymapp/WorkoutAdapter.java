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
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.RecyclerViewHolder> {

    private List<Exercises> courseDataArrayList;
    private Context mcontext;

    private WorkoutListener workoutListener;


    public WorkoutAdapter(List<Exercises> recyclerDataArrayList, Context mcontext, WorkoutListener workoutListener) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
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


        holder.woExerciseName.setText(courseDataArrayList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }


    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView woExerciseName;
        private Button addSet;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            addSet = itemView.findViewById(R.id.wo_add_set);
        }
    }
}
