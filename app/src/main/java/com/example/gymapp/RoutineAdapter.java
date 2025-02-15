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

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RecyclerViewHolder> {

    private List<Routines> courseDataArrayList;
    private Context mcontext;

    private RoutineListener routineListener;

    public RoutineAdapter(List<Routines> routines, Context mcontext,RoutineListener listener) {

        if(routines == null){
            courseDataArrayList = new ArrayList<>();
        }
        else{
            this.courseDataArrayList = routines;
        }

        this.mcontext = mcontext;
        this.routineListener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rou_exe_card_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview
        Routines recyclerData = courseDataArrayList.get(position);
        holder.splitName.setText(recyclerData.getName());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routineListener.deleteButton(recyclerData);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routineListener.editButton(recyclerData);
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
        private ImageButton delete,edit;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            splitName = itemView.findViewById(R.id.splitNameText);

            delete=itemView.findViewById(R.id.deleteDataButton);
            edit = itemView.findViewById(R.id.editDataButton);


        }
    }
}
