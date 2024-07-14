package com.example.gymapp;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.RecyclerViewHolder> {

    final private List<Sets> data;

    Context mcontext;
    Workout previous;
    Exercises exercises;
    ISetListener setListener;


    public DialogAdapter(List<Sets> sets, Context context, Optional<Workout> previousWorkout, Exercises exercises, ISetListener setListener) {

        data = sets;
        this.mcontext = context;
        this.exercises = exercises;
        this.setListener = setListener;

        previous = previousWorkout.orElse(null);

    }

    @NonNull
    @Override
    public DialogAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_rows, parent, false);

        return new DialogAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogAdapter.RecyclerViewHolder holder, int position) {

        List<Sets> prevSets = getPreviousSets();

        if(prevSets.size() != 0 && position<= prevSets.size()-1){
            holder.prev.setText(String.valueOf(prevSets.get(position).getReps()));
            holder.kg.setText(String.valueOf(prevSets.get(position).getKg()));
        }else{
            holder.prev.setText(String.valueOf(0));
        }

        holder.id.setText(String.valueOf(position+1));

        if(data.get(position).getKg()>0){
            holder.kg.setText(String.valueOf(data.get(position).getKg()));
        }
        if(data.get(position).getReps()>0){
            holder.reps.setText(String.valueOf(data.get(position).getReps()));
        }

        holder.kg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!holder.kg.getText().toString().isEmpty()){
                    data.get(holder.getAdapterPosition()).setKg(Integer.parseInt(holder.kg.getText().toString()));
                    setListener.setChanged(exercises, data.get(holder.getAdapterPosition()));
                }
            }
        });

        holder.reps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!holder.reps.getText().toString().isEmpty()){
                    data.get(holder.getAdapterPosition()).setReps(Integer.parseInt(holder.reps.getText().toString()));
                    setListener.setChanged(exercises, data.get(holder.getAdapterPosition()));
                }
            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setListener.setDeleted(exercises,data.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Sets> getPreviousSets(){
        List<Sets> test = new ArrayList<>();
        if(previous != null){
            for (Exercises item:previous.getExercises().keySet()) {
                if(item.getName().equals(exercises.getName())){
                    test = previous.getExercises().get(item);
                    return test;
                }
            }
        }
        return test;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView id,prev;
        EditText kg,reps;
        ImageButton del;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.wo_setId);
            prev = itemView.findViewById(R.id.wo_Previous);
            kg = itemView.findViewById(R.id.wo_KG);
            reps = itemView.findViewById(R.id.wo_Reps);
            del = itemView.findViewById(R.id.removeSet);
        }
    }
}
