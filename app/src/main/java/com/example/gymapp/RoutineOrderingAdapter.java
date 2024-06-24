package com.example.gymapp;

import android.content.Context;
import android.media.Image;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoutineOrderingAdapter extends RecyclerView.Adapter<RoutineOrderingAdapter.RecyclerViewHolder> implements ItemTouchHelperAdapter {

    private List<Routines> courseDataArrayList;

    Split split;
    private Context mcontext;

    private ItemTouchHelper itemTouchHelper;
    MyDatabase db;




    public RoutineOrderingAdapter(Split split, Context mcontext, MyDatabase db) {


        this.mcontext = mcontext;
        this.db = db;
        this.split = split;
        courseDataArrayList = split.getRoutinesList();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_order, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview
        Routines recyclerData = courseDataArrayList.get(position);
        holder.name.setText(recyclerData.getName());

    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return courseDataArrayList.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Routines from  = courseDataArrayList.get(fromPosition);
        courseDataArrayList.remove(fromPosition);
        courseDataArrayList.add(toPosition,from);
        notifyItemMoved(fromPosition,toPosition);
        db.splitDao().updateRoutines(courseDataArrayList, split.getName());
    }

    public void setItemTouchHelper(ItemTouchHelper touchHelper){
        this.itemTouchHelper = touchHelper;
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener, GestureDetector.OnGestureListener {

        private TextView name;
        GestureDetector gestureDetector;


        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.routineName);

            gestureDetector = new GestureDetector(itemView.getContext(),this);
            itemView.setOnTouchListener(this);


        }

        @Override
        public boolean onDown(@NonNull MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(@NonNull MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(@NonNull MotionEvent e) {

            return false;
        }

        @Override
        public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            itemTouchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            return true;
        }
    }
}
