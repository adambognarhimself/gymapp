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

import java.util.List;

public class WorkoutRowsAdapter extends RecyclerView.Adapter<WorkoutRowsAdapter.RecyclerViewHolder> {

    private List<Sets> courseDataArrayList;
    private Context mcontext;

    private ISetListener iSetListener;
    Exercises name;




    public WorkoutRowsAdapter(Exercises name, List<Sets> recyclerDataArrayList, Context mcontext, ISetListener iSetListener ) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
        this.iSetListener = iSetListener;
        this.name = name;
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


        holder.woSetID.setText(String.valueOf(position+1));
        holder.wo_KG.setText(String.valueOf(sets.getKg()));
        holder.wo_Prev.setText(String.valueOf(sets.getReps()));
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSetListener.removeSet(name,sets);
            }
        });

        holder.wo_Reps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!holder.wo_Reps.getText().toString().equalsIgnoreCase("")){
                    iSetListener.saveKG(name,sets.getSet(),Integer.parseInt(holder.wo_Reps.getText().toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.wo_KG.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!holder.wo_KG.getText().toString().equalsIgnoreCase("")){
                    iSetListener.saveKG(name,sets.getSet(),Integer.parseInt(holder.wo_KG.getText().toString()));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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

        private TextView woSetID;
        private TextView wo_Prev;
        private EditText wo_KG;
        private EditText wo_Reps;

        private ImageButton remove;






        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            woSetID = itemView.findViewById(R.id.wo_setId);
            wo_Prev = itemView.findViewById(R.id.wo_Previous);
            wo_KG = itemView.findViewById(R.id.wo_KG);
            wo_Reps = itemView.findViewById(R.id.wo_Reps);
            remove = itemView.findViewById(R.id.removeSet);

        }


    }
}
