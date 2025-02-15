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

public class SplitAdapter extends RecyclerView.Adapter<SplitAdapter.RecyclerViewHolder> {

    private List<Split> courseDataArrayList;
    private Context mcontext;
    private SplitListener splitListener;



    public SplitAdapter(List<Split> recyclerDataArrayList, Context mcontext, SplitListener listener) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
        this.splitListener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.splitcard_layout, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview
        Split recyclerData = courseDataArrayList.get(position);
        holder.splitName.setText(recyclerData.getName());
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splitListener.editButton(recyclerData);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splitListener.deleteButton(recyclerData);
            }
        });

        holder.selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splitListener.selectButton(recyclerData);
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
        private ImageButton editButton, deleteButton, selectButton;



        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            splitName = itemView.findViewById(R.id.splitNameText);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            selectButton = itemView.findViewById(R.id.selectSplitButton);

        }


    }
}
