package com.example.jonatan.clothesplanner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jonatan.clothesplanner.wardrobe.IWardrobe;
import com.example.jonatan.clothesplanner.wardrobe.Wardrobe;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Wardrobe
        Wardrobe.initInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void goToWardrobe(View view) {
        Intent intent = new Intent(this, WardrobeActivity.class);
        startActivity(intent);
    }

    public void goToWeeklyPlan(View view) {
        Intent intent = new Intent(this, WeeklyPlanActivity.class);
        startActivity(intent);
    }
}
