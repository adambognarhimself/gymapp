package com.example.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.RecyclerViewHolder> implements ISetListener{

    private List<Exercises>  courseDataArrayList;
    private Context mcontext;

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();


    MyDatabase db;
    WorkoutListener workoutListener;

    WorkoutRowsAdapter adapter;
    List<Sets> sets;
    Workout previous;

    private ISetListener iSetListener;


    public WorkoutAdapter(List<Exercises> recyclerDataArrayList, Context mcontext, Workout prev,WorkoutListener workoutListener,ISetListener iSetListener) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
        db = MyDatabase.getINSTANCE(mcontext);
        this.workoutListener = workoutListener;
        previous = prev;
        this.iSetListener = iSetListener;
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
        holder.addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutListener.addNewSet(currentName);
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutListener.removeExercise(currentName);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.child.getContext());
       //layoutManager.setInitialPrefetchItemCount(previous.getExercises().get(currentName).size());


       adapter = new WorkoutRowsAdapter(currentName,previous.getExercises().get(currentName),mcontext,this);
        holder.child.setLayoutManager(layoutManager);
        holder.child.setAdapter(adapter);
       holder.child.setRecycledViewPool(viewPool);

    }




    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    @Override
    public void removeSet(Exercises exercises,Sets sets) {
//        previous.getExercises().get(exercises).remove(sets);
//        adapter.notifyDataSetChanged();

        iSetListener.removeSet(exercises,sets);

    }


    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView woExerciseName;

    private RecyclerView child;
        private Button addSet;

        private ImageButton remove;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            woExerciseName = itemView.findViewById(R.id.wo_exercise_name);
            addSet = itemView.findViewById(R.id.wo_add_set);
            child = itemView.findViewById(R.id.workout_row_rec);
            remove = itemView.findViewById(R.id.removeExercise);

        }
    }
}
