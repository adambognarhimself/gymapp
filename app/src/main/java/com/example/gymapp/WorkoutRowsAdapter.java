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

public class WorkoutRowsAdapter extends RecyclerView.Adapter<WorkoutRowsAdapter.RecyclerViewHolder> {

    private List<Sets> courseDataArrayList;
    private Context mcontext;




    public WorkoutRowsAdapter(List<Sets> recyclerDataArrayList, Context mcontext ) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_rows, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview

        Sets sets = courseDataArrayList.get(position);


        holder.woSetID.setText(String.valueOf(position));
        holder.wo_KG.setText(String.valueOf(sets.getKg()));
        holder.wo_Reps.setText(String.valueOf(sets.getReps()));
        //holder.wo_Prev.setText(String.valueOf(sets.));



    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }


    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView woSetID;
        private TextView wo_Prev;
        private TextView wo_KG;
        private TextView wo_Reps;





        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            woSetID = itemView.findViewById(R.id.wo_setId);
            wo_Prev = itemView.findViewById(R.id.wo_Previous);
            wo_KG = itemView.findViewById(R.id.wo_KG);
            wo_Reps = itemView.findViewById(R.id.wo_Reps);

        }


    }
}
