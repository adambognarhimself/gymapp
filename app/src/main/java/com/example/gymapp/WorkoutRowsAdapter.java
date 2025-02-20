package com.example.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutRowsAdapter extends RecyclerView.Adapter<WorkoutRowsAdapter.RecyclerViewHolder> {

    private final List<Sets> setsList;
    private final Context context;

    private final List<Sets> prevSets;
    private final Boolean showPrevious;


    public WorkoutRowsAdapter(List<Sets> sets, List<Sets> prevSets, Context context,Boolean showPrevious){
    this.setsList = sets;
    this.context = context;
    this.prevSets = prevSets;
    this.showPrevious = showPrevious;
    }

    public WorkoutRowsAdapter(List<Sets> sets, Context context,Boolean showPrevious){
        this.setsList = sets;
        this.context = context;
        prevSets = null;
        this.showPrevious = showPrevious;
    }

    @NonNull
    @Override
    public WorkoutRowsAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noneditable_workout_rows, parent, false);

        return new WorkoutRowsAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutRowsAdapter.RecyclerViewHolder holder, int position) {

        if(!showPrevious) holder.prev.setVisibility(View.GONE);

        if(prevSets != null && position <= prevSets.size()-1){
            holder.prev.setText(String.valueOf(prevSets.get(position).getReps()));
            holder.kg.setText(String.valueOf(prevSets.get(position).getKg()));
        }else{
            holder.prev.setText(String.valueOf(0));
            holder.kg.setText(String.valueOf(0));
        }
        holder.id.setText(String.valueOf(position+1));
        holder.kg.setText(String.valueOf(setsList.get(position).getKg()));
        holder.reps.setText(String.valueOf(setsList.get(position).getReps()));

    }

    @Override
    public int getItemCount() {
        return setsList.size();
    }



    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView id,prev,kg,reps;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.wo_setId);
            prev = itemView.findViewById(R.id.wo_Previous);
            kg = itemView.findViewById(R.id.wo_KG);
            reps = itemView.findViewById(R.id.wo_Reps);

        }
    }
}
