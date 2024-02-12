package com.example.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.RecyclerViewHolder> {

    private List<Exercises> courseDataArrayList;
    private Context mcontext;



    public ChooseAdapter(List<Exercises> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_box, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview
        Exercises recyclerData = courseDataArrayList.get(position);
        holder.splitName.setText(recyclerData.getName());


    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }


    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView splitName;



        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            splitName = itemView.findViewById(R.id.exerciseName);

        }


    }
}
