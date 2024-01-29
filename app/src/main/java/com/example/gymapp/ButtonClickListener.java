package com.example.gymapp;

import android.view.View;
import android.widget.Toast;

public class ButtonClickListener implements View.OnClickListener {

    private int position;

    public ButtonClickListener(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        // Handle button click here
        // Get the position of the clicked item
        int clickedPosition = position;

        // Use the clickedPosition to perform your desired action
        // For example, display a toast message
        Toast.makeText(v.getContext(), "Clicked item position: " + clickedPosition, Toast.LENGTH_SHORT).show();
    }
}
