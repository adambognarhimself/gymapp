package com.example.gymapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.telephony.ims.stub.ImsRegistrationImplBase;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class RoutinesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routines);


        ImageButton back = findViewById(R.id.routineBackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}