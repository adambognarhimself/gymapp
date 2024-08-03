package com.example.gymapp.ui.notifications;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymapp.CalendarAdapter;
import com.example.gymapp.DayModel;
import com.example.gymapp.MyDatabase;
import com.example.gymapp.R;
import com.example.gymapp.Workout;
import com.example.gymapp.databinding.FragmentNotificationsBinding;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotificationsFragment extends Fragment implements CalendarAdapter.IOnItemListener {

    private FragmentNotificationsBinding binding;
    private TextView monthYearText, workoutName;
    private RecyclerView calendarRecyclerView,dialogRecyclerview;
    private LocalDate selectedDate;
    private List<Workout> workouts; // Assume this is populated with your workout data
    private Dialog dialog;
    private WokourtDialogAdapter dialogAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Assume workouts are populated from your database or other data source
        workouts = getWorkouts();


        initDialog();
        initWidgets(root);

        selectedDate = LocalDate.now();
        setMonthView();


        binding.previousMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousMonthAction(v);
            }
        });

        binding.nextMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonthAction(v);
            }
        });



        return root;
    }

    private void initWidgets(View root) {
        calendarRecyclerView = root.findViewById(R.id.cardView2);
        monthYearText = root.findViewById(R.id.exerciseName);
        dialogRecyclerview = dialog.findViewById(R.id.setsView);
        workoutName = dialog.findViewById(R.id.workoutName);

    }

    private void initDialog(){
        dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.show_workout_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

    }

    private void initDialogRecyclerView(Workout workout){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        dialogRecyclerview.setLayoutManager(layoutManager);

        dialogAdapter = new WokourtDialogAdapter(workout, getContext());
        dialogRecyclerview.setAdapter(dialogAdapter);
    }

    private void dialogAnimation() {
        FrameLayout root = dialog.findViewById(android.R.id.content);
        Animation up = AnimationUtils.loadAnimation(this.getContext(),R.anim.slideup);
        Animation down = AnimationUtils.loadAnimation(this.getContext(),R.anim.slidedown);
        root.startAnimation(up);

        dialog.findViewById(R.id.collapseButton).setOnClickListener(view -> {
            // Apply slide-down animation before dismissing the dialog
            down.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    // Do nothing
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // Dismiss the dialog after the animation ends
                    dialog.dismiss();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // Do nothing
                }
            });
            root.startAnimation(down);

        });
    }


    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<DayModel> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this,this.getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<DayModel> daysInMonthArray(LocalDate date) {
        ArrayList<DayModel> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = date.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        Set<LocalDate> workoutDates = new HashSet<>();
        for (Workout workout : workouts) {
            workoutDates.add(workout.getDate());
        }

        for (int i = 2; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(new DayModel("", "", false));
            } else {
                LocalDate dayDate = firstOfMonth.plusDays(i - dayOfWeek - 1);
                String dayOfWeekStr = dayDate.getDayOfWeek().name().substring(0, 3);
                boolean hasWorkout = workoutDates.contains(dayDate);
                daysInMonthArray.add(new DayModel(String.valueOf(i - dayOfWeek), dayOfWeekStr, hasWorkout));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public LocalDate SelectedDate(String day){
        return LocalDate.of(selectedDate.getYear(),selectedDate.getMonth(),Integer.parseInt(day));
    }

    @Override
    public void onItemClick(int pos, String day) {
        if (!day.equals("")) {

            LocalDate date = SelectedDate(day);

            Set<LocalDate> workoutDates = new HashSet<>();
            for (Workout workout : workouts) {
                workoutDates.add(workout.getDate());
            }

            if(workoutDates.contains(date)){
                Workout selected = null;
                for (Workout item: workouts) {
                    if(item.getDate().equals(date)){
                        selected = item;
                        break;
                    }
                }
                dialogAnimation();
                dialog.show();
                workoutName.setText(selected.getDesc());
                initDialogRecyclerView(selected);
            }
        }
    }

    private List<Workout> getWorkouts() {
        // Implement this to retrieve your workouts from the database or other data source
        MyDatabase db = MyDatabase.getINSTANCE(this.getContext());
        return db.workoutDao().getall();
    }
}
