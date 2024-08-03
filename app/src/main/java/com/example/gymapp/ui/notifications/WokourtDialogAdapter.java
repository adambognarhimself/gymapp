package com.example.gymapp.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.Exercises;
import com.example.gymapp.R;
import com.example.gymapp.Workout;
import com.example.gymapp.WorkoutRowsAdapter;

import java.util.ArrayList;
import java.util.List;

public class WokourtDialogAdapter extends RecyclerView.Adapter<WokourtDialogAdapter.RecyclerViewHolder> {

    final private Workout data;
    Context mcontext;
    private WorkoutRowsAdapter adapter;
    private final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();


    public WokourtDialogAdapter(Workout data, Context mcontext) {
        this.data = data;
        this.mcontext = mcontext;
    }


    @NonNull
    @Override
    public WokourtDialogAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_box, parent, false);

        return new WokourtDialogAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WokourtDialogAdapter.RecyclerViewHolder holder, int position) {

        List<Exercises> exercises = new ArrayList<>(data.getExercises().keySet());

        Exercises currentExercise = exercises.get(position);

        holder.exerciseName.setText(currentExercise.getName());



        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.child.getContext());

        adapter = new WorkoutRowsAdapter(data.getExercises().get(currentExercise),mcontext,false);

        holder.child.setLayoutManager(layoutManager);
        holder.child.setAdapter(adapter);
        holder.child.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return data.getExercises().size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        final private TextView exerciseName;
        private final RecyclerView child;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            this.exerciseName = itemView.findViewById(R.id.wo_exercise_name);
            child = itemView.findViewById(R.id.workout_row_rec);


        }
    }
}
