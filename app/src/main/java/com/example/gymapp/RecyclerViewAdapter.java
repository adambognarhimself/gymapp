package com.example.gymapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<Workout> courseDataArrayList;
    private Context mcontext;

    public RecyclerViewAdapter(ArrayList<Workout> recyclerDataArrayList, Context mcontext) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview
     Workout recyclerData = courseDataArrayList.get(position);
       holder.name.setText(recyclerData.getDesc());
       holder.duration.setText(Integer.toString(recyclerData.getDuration()));
       if(recyclerData.getDate() != null){
        holder.date.setText(Integer.toString(recyclerData.getDate().getYear()));
       }else holder.date.setText("Null");
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView duration;
        private TextView date;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameText);
            duration = itemView.findViewById(R.id.durationText);
            date = itemView.findViewById(R.id.dateText);

        }
    }
}
