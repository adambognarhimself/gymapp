package com.example.gymapp;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.RecyclerViewHolder> {

    List<DayModel> data;
    private final IOnItemListener onItemListener;
    Context context;
    MyDatabase db;

    public CalendarAdapter(List<DayModel> data, IOnItemListener onItemListener, Context context) {
        this.data = data;
        this.onItemListener = onItemListener;
        this.context = context;
        db = MyDatabase.getINSTANCE(context);
    }

    @NonNull
    @Override
    public CalendarAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_view, parent, false);
        view.getLayoutParams().height = (int)(parent.getHeight() * 0.13);
        return new CalendarAdapter.RecyclerViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.RecyclerViewHolder holder, int position) {
        holder.day.setText(data.get(position).getDate());
        holder.dayOfWeek.setText(data.get(position).getDayOfWeek());
        if(data.get(position).getDate().equals("")){
            int color = ContextCompat.getColor(context,R.color.black);
            holder.color.setBackgroundColor(color);
        }

        DayModel dayModel = data.get(position);

        if(dayModel.hasWorkout()){
            int color = ContextCompat.getColor(context,R.color.myGreenLighter);
            holder.color.setBackgroundResource(R.drawable.calendar_background_green);

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView day, dayOfWeek;
        LinearLayout color;
        private final IOnItemListener onItemListener;

        public RecyclerViewHolder(@NonNull View itemView, IOnItemListener onItemListener) {
            super(itemView);
            day = itemView.findViewById(R.id.cellDayText);
            dayOfWeek = itemView.findViewById(R.id.cellDayOfWeekText);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
            color = itemView.findViewById(R.id.calendar_background);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition(), day.getText().toString());
        }
    }

    public interface IOnItemListener {
        void onItemClick(int pos, String day);
    }
}
