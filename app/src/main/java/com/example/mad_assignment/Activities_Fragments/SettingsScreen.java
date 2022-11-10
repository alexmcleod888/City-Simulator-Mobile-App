package com.example.mad_assignment.Activities_Fragments;

//imports
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mad_assignment.Data.GameData;
import com.example.mad_assignment.Data.Settings;
import com.example.mad_assignment.R;

/*Author: Alex McLeod
  Purpose: Activity class which displays the current map width and height of the grid map and
           also displays the starting money for the player
  Data Modified: 14/10/2019
 */
public class SettingsScreen extends AppCompatActivity {

    //private TextView objects that are used to display the current settings
    private TextView currentWidth;
    private TextView currentHeight;
    private TextView currentMoney;

    //private text views that allow the user to enter plain text, for what that would like to
    //change the width, height and initial money too
    private TextView editWidth;
    private TextView editHeight;
    private TextView editMoney;

    //reference to button used to save information when settings are changed
    private Button saveBtn;

    //reference to current game data
    private GameData gameData;
    //reference to current game settings
    private Settings settings;

    //boolean used to check if the user has entered valid changes
    private Boolean updateFail;

    //String for holding any error messages
    private String errorString;

    //purpose: on create method that initialises the settings screen and the
    //         save button on click listener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        //obtain references to text views
        currentWidth = findViewById(R.id.currentMapWidth);
        currentHeight = findViewById(R.id.currentMapHeight);
        currentMoney = findViewById(R.id.initialMoney);
        editWidth = findViewById(R.id.editWidth);
        editHeight = findViewById(R.id.editHeight);
        editMoney = findViewById(R.id.editInitialMoney);

        //obtain reference to save button
        saveBtn = findViewById(R.id.saveBtn);

        //obtain reference to the current game data
        gameData = GameData.get(this);

        //obtain a reference to the current game settings
        settings = gameData.getSettings();

        //set the Strings that display the current width and height of the new grid layout as well
        //as the initial money
        currentWidth.setText("Map Width: " + Integer.toString(settings.getWidth()));
        currentHeight.setText("Map Height: " + Integer.toString(settings.getHeight()));
        currentMoney.setText("Initial Money: " + Integer.toString(settings.getInitialMoney()));


        //create an on click listener for when the user wants to save there changes to the settings
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newWidth, newHeight, newMoney;

                //currently there has been no fails to updating so boolean has been set to false
                updateFail = false;
                //initialise an empty string for when any errors occur and need to saved
                errorString = new String("");

                try {
                    //get the width if the user has entered a new width value
                    newWidth = Integer.parseInt(editWidth.getText().toString());
                    //if the width is between 1 and 100 then set this as the new width
                    if (newWidth > 0 && newWidth <= 100) {
                        gameData.setWidth(newWidth);
                        currentWidth.setText("Map Width: " + Integer.toString(newWidth));
                    //otherwise add an error to the String of error messages and set boolean to true
                    //as there has been an error
                    } else {
                        errorString += "Error: Map width must be between 1 - 100\n ";
                        updateFail = true;
                    }
                }
                //if the user doesn't enter information into the text view there is default text
                //so ignore this
                catch(NumberFormatException e){}


                try {
                    //get the height if the user has entered a new height
                    newHeight = Integer.parseInt(editHeight.getText().toString());
                    //if the height is between 1 and 30 then set as the new height
                    if (newHeight > 0 && newHeight <= 30) {
                        gameData.setHeight(newHeight);
                        currentHeight.setText("Map Height: " + Integer.toString(newHeight));
                    //otherwise add to the String of errors and set boolean to true if it hasnt
                    //already
                    } else {
                        errorString += "Error: Map height must be between 1 - 30\n ";
                        updateFail = true;
                    }
                }
                //if the user doesn't enter information into the text view there is default text
                //so ignore this
                catch(NumberFormatException e){}


                try {
                    //get the new initial money if the user has entered a new amount
                    newMoney = Integer.parseInt(editMoney.getText().toString());
                    //if the amount of money is greater than 0 and less than 1000000
                    //set it as the new initial amount
                    if (newMoney > 0 && newMoney <= 1000000) {
                        gameData.setInitialMoney(newMoney);
                        currentMoney.setText("Initial Money: " + Integer.toString(newMoney));
                    //otherwise add to error String and set boolean to true
                    } else {
                        errorString += "Error: Integer must be positive and less than 1 million\n";
                        updateFail = true;
                    }
                }
                //if the user doesn't enter information into the text view there is default text
                //so ignore this
                catch(NumberFormatException e){}

                //if there has been an error output toast message to notify user
                if(updateFail == true)
                {
                    Toast.makeText(getApplicationContext(), errorString,
                            Toast.LENGTH_LONG).show();
                }
                //update the game settings if there has been any changes
                gameData.updateSettings();
            }
        });

    }
}
