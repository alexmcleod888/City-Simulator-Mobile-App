package com.example.mad_assignment.Activities_Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mad_assignment.Data.GameData;
import com.example.mad_assignment.Data.Settings;
import com.example.mad_assignment.R;
import com.example.mad_assignment.Data.Structure;

/*Author: Alex McLeod
  Purpose: Activity class for holding the two fragment contain the two scrollable recycler views
           containing the map and the structures that can be placed on the map. It also
           acts to display the current game details and buttons that activate particular
           interactions with grid elements.
  Date Modified: 15/10/2019
 */

public class MapScreen extends AppCompatActivity {

    // String which is displayed when the user runs out of money
    private static final String GAME_OVER = "Game Over";

    // declare and initialise the MapScreen's text views
    private static TextView gameOverView = null;
    private static TextView currentMoneyView = null;
    private static TextView currentPopulation = null;
    private static TextView currentEmploymentRate = null;
    private static TextView recentIncome = null;
    private static TextView resCost = null;
    private static TextView comCost = null;
    private static TextView roadCost = null;

    // booleans used to check whether a particular button has been pressed
    private static boolean demoOn = false;
    private static boolean detailsOn = false;

    //TextView used to hold the current time
    private TextView currentTime;

    //reference to current settings of the game
    private Settings settings;

    //private static variable referencing the current game data
    private static GameData gameData;

    //button used to increase the current time passed
    private Button runBtn;
    //buttons that the user can click to produce particular interactions with grid cells and
    private Button demoBtn;
    private Button detailsBtn;

    //purpose: upon creation of the map screen retrieve text views and buttons. Sets
    //on click listeners for map screen buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);

        //retrieve references to text views
        currentMoneyView = findViewById(R.id.currentMoney);
        gameOverView = findViewById(R.id.gameOverView);
        currentPopulation = findViewById(R.id.currentPopulation);
        currentTime = findViewById(R.id.currentTime);
        currentEmploymentRate = findViewById(R.id.employmentRate);
        recentIncome = findViewById(R.id.recentIncome);
        resCost = findViewById(R.id.resCost);
        comCost = findViewById(R.id.comCost);
        roadCost = findViewById(R.id.roadCost);

        //retrieve references to buttons
        runBtn = findViewById(R.id.runBtn);
        demoBtn = findViewById(R.id.demoBtn);
        detailsBtn = findViewById(R.id.detailsBtn);

        //set reference to current game data and settings
        gameData = GameData.get(this);
        settings = gameData.getSettings();

        //set text of the text views using gameData information
        currentTime.setText("Current Time: " + gameData.getTime());
        currentMoneyView.setText("Current Money: " + gameData.getCurrentMoney());
        currentPopulation.setText("Current Population: " + gameData.getPopulation());
        currentEmploymentRate.setText("Current Employment Rate: " + gameData.getEmploymentRatePercentage() + "%");
        recentIncome.setText("Recent Income: " + gameData.getIncome());
        resCost.setText("Residential Cost: " + settings.getHouseCost());
        comCost.setText("Commercial Cost: " + settings.getCommCost());
        roadCost.setText("Road Cost: " + settings.getRoadBuildingCost());

        //get fragment manager to keep track of different fragments
        FragmentManager fm  = getSupportFragmentManager();
        //retrieve selector fragment for holding recycler view of different structures
        Selector mySelector = (Selector) fm.findFragmentById(R.id.selector);
        //if selector hasn't been created yet create a new Selector
        if(mySelector == null)
        {
            mySelector = new Selector();
            //add and commit fragment to initialise changes to
            fm.beginTransaction().add(R.id.selector, mySelector).commit();

        }
        //retrieve map fragment for holding grid layout of map
        Map map = (Map) fm.findFragmentById(R.id.map);
        //if map hasn't been constructed, create a new Map object
        if(map == null)//check if its already there
        {
            map = new Map();
            fm.beginTransaction().add(R.id.map, map).commit();
        }

        //set maps reference to the selector object
        map.setSelectorFragment(mySelector);

        //if run button is selected increase the current time and update the current amount of money
        runBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameData.increaseTime();
                currentTime.setText("Current Time: " + Integer.toString(gameData.getTime()));
                updateMoney();

            }
        });

        //if demo button is clicked initialise demo mode
        demoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsOn = false;
                demoOn = true;
            }
        });

        //if details button is clicked initialise details mode
        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demoOn = false;
                detailsOn = true;
            }
        });

    }

    //update the current amount of money as the grid changes
    public void updateMoney()
    {
        int currentMoney, income;

        //updates values within gameData
        gameData.updateIncome();
        //set the maps current state
        income = gameData.getIncome();
        currentMoney = gameData.getCurrentMoney();
        currentMoney += income;
        recentIncome.setText("Recent Income: " + Integer.toString(gameData.getIncome()));

        //if the current amount of money has reached 0 then display game over text
        if(currentMoney <= 0)
        {
            currentMoney = 0;
            gameOverView.setText(GAME_OVER);
        }

        //set current money
        gameData.setCurrentMoney(currentMoney);
        currentMoneyView.setText("Current Money: " + Integer.toString(gameData.getCurrentMoney()));

    }

    //purpose: set the current amount of money after a new structure has been placed
    public static void setMoney(int cost)
    {
        int currentMoney = gameData.getCurrentMoney();//get the current amount of money in the game
        currentMoney -= cost;// negate the cost of newly placed structure from the current amount
                             // of money
        //if the current amount of money is 0 or less output game over message
        if(currentMoney <= 0)
        {
            currentMoney = 0;
            gameOverView.setText(GAME_OVER);
        }
        gameData.setCurrentMoney(currentMoney);//set the current money within game data
        //set text view of current money
        currentMoneyView.setText("Current Money: " + Integer.toString(gameData.getCurrentMoney()));
    }

    //purpose: set the number of structures as a new structure is added to the map
    public static void setNumStructures(Structure structure)
    {
        //increase the total number of that particular structures
        structure.increaseTotal();

        gameData.updateNumStructures();//update number of structure
        gameData.updateIncome();
        //update text views
        currentPopulation.setText("Current Population: " + Integer.toString(gameData.getPopulation()));
        currentEmploymentRate.setText("Current Employment Rate: " + Double.toString(gameData.getEmploymentRatePercentage()) + "%");
        recentIncome.setText("Recent Income: " + Integer.toString(gameData.getIncome()));
    }

    //purpose: when a structure is removed decrease the number of structures
    public static void decreaseStructureCount(Structure structure)
    {
        structure.decreaseTotal();
        gameData.updateNumStructures();
        gameData.updateIncome();
        currentPopulation.setText("Current Population: " + Integer.toString(gameData.getPopulation()));
        currentEmploymentRate.setText("Current Employment Rate: " + Double.toString(gameData.getEmploymentRatePercentage()) + "%");
        recentIncome.setText("Recent Income: " + Integer.toString(gameData.getIncome()));
    }

    //purpose: checking if the demolish button has been pressed
    public static boolean checkDemo()
    {
        return demoOn;
    }

    //purpose: method for setting demo to false when demolish mode has been turned off
    public static void turnDemoOff()
    {
        demoOn = false;
    }

    //purpose: method for checking if details button has been pressed
    public static boolean checkDetails()
    {
        return detailsOn;
    }

    //purpose: method for setting details button to false when details mode has been turned off
    public static void turnDetailsOff()
    {
        detailsOn = false;
    }



}
