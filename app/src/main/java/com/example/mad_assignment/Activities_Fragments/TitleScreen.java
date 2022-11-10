package com.example.mad_assignment.Activities_Fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mad_assignment.Database.GameDbHelper;
import com.example.mad_assignment.R;

/*Author: Alex McLeod
  Purpose: Activity class that displays the different options to the user allowing the user
           to either go to the edit settings menu or go to map screen
  Date Modified: 15/10/2019
 */

public class TitleScreen extends AppCompatActivity {

    //reference to the buttons that allow the user to select a particular option
    private Button mapLaunchBtn;
    private Button settingBtn;


    //purpose: initialises the Title screen and onclick listeners for the map launch button
    //         and settings button.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        //GameDbHelper gameDbHelper = new GameDbHelper(this);

        // get the references to the two buttons
        mapLaunchBtn = (Button) findViewById(R.id.mapLaunchBtn);
        settingBtn = (Button) findViewById(R.id.settingsBtn);

        // create an on click listener for when a user presses the  map launch button
        // this launches the MapScreen activity
        mapLaunchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TitleScreen.this, MapScreen.class));
            }
        });

        // create an on click listener for when a user presses the settings button
        // this launches the Settings Screen
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TitleScreen.this, SettingsScreen.class));
            }
        });

    }
}
