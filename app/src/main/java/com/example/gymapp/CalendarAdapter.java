package com.example.gymapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.RecyclerViewHolder> {


    List<String> data;

    public CalendarAdapter(List<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CalendarAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_view, parent, false);
        view.getLayoutParams().height = (int)(parent.getHeight()*1.66666666);
        return new CalendarAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.RecyclerViewHolder holder, int position) {

        holder.day.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView day;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.cellDayText);
        }
    }
}
