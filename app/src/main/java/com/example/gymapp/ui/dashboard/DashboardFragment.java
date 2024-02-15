package com.example.gymapp.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gymapp.ChooseExercisesActivity;
import com.example.gymapp.R;
import com.example.gymapp.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    ImageButton startStop, add;
    boolean timerStarted = false;
    CountDownTimer timer;
    long elapsedTime = 0;
    TextView timerText;

    Context context;

    /*ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if(o.getResultCode() == Activity.RESULT_OK){
                        Intent data = o.getData();
                        String returned = data.getDataString();

                        Toast.makeText(context,returned,Toast.LENGTH_SHORT).show();

                    }
                }
            });*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getContext();

        timerText = root.findViewById(R.id.durationText);

        timer = new CountDownTimer(Long.MAX_VALUE,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                elapsedTime += 1000;
                updateTimerText();
            }

            @Override
            public void onFinish() {

            }
        };

        startStop = root.findViewById(R.id.startStopButton);
        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!timerStarted){
                    timer.start();
                    timerStarted = true;
                    startStop.setImageResource(R.drawable.baseline_stop_circle_24);


                }
                else{
                timer.cancel();
                timerStarted = false;

                elapsedTime = 0;
                updateTimerText();
                    startStop.setImageResource(R.drawable.baseline_play_circle_24);


                }




        }});

        add = root.findViewById(R.id.exerciseAddButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChooseExercisesActivity.class);
                startActivity(intent);

                //activityResultLauncher.launch(intent);
            }
        });




        return root;
    }

    private void updateTimerText() {

        int seconds = (int) (elapsedTime / 1000) % 60;
        int minutes = (int) ((elapsedTime / (1000 * 60)) % 60);
        int hours = (int) ((elapsedTime / (1000 * 60 * 60)) % 24);
        String timeString = String.format("%2d:%02d:%02d", hours, minutes, seconds);
        timerText.setText(timeString);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}