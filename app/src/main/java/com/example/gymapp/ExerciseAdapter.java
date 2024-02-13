package com.example.gymapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.RecyclerViewHolder> {

    private List<Exercises> courseDataArrayList;
    private Context mcontext;

    private ExerciseListener routineListener;

    public ExerciseAdapter(List<Exercises> exercises, Context mcontext,ExerciseListener listener) {
        if(exercises == null){
            courseDataArrayList = new ArrayList<>();
        }else{
        this.courseDataArrayList = exercises;
        }
        this.mcontext = mcontext;
        this.routineListener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exe_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview
        Exercises recyclerData = courseDataArrayList.get(position);
        holder.splitName.setText(recyclerData.getName());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routineListener.deleteButton(recyclerData);
            }
        });



    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView splitName;
        private ImageButton delete;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            splitName = itemView.findViewById(R.id.splitNameText);

            delete=itemView.findViewById(R.id.deleteDataButton);


        }
    }
}
