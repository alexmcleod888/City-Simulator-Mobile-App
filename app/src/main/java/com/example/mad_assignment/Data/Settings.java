package com.example.mad_assignment.Data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.mad_assignment.Database.GameDatabaseSchema.SettingsTable;
import com.example.mad_assignment.Database.GameDbCursor;

/*Author: Alex McLeod
  Purpose: model class for storing, accessing and modifying setting information for the game
  Date Modified: 15/10/2019
 */

public class Settings {

    //different game settings
    private int mapWidth;
    private int mapHeight;
    private int initialMoney;
    private int familySize;
    private int shopSize;
    private int salary;
    private double taxRate;
    private int serviceCost;
    private int houseBuildingCost;
    private int commBuildingCost;
    private int roadBuildingCost;

    //reference to the database of the app
    private SQLiteDatabase db;

    //default constructor for setting default values for game settings
    public Settings()
    {
        mapWidth = 50;
        mapHeight = 10;
        initialMoney = 1000;
        familySize = 4;
        shopSize = 6;
        salary = 10;
        taxRate = 0.3;
        serviceCost = 2;
        houseBuildingCost = 100;
        commBuildingCost = 500;
        roadBuildingCost = 20;
    }

    //constructor for creating a new Setting object based on the setting information loaded from a
    //database. this setting information being a map width and height, as well as an initial amount
    //of money
    public Settings(int newWidth, int newHeight, int newInitialMoney)
    {
        mapWidth = newWidth;
        mapHeight = newHeight;
        initialMoney = newInitialMoney;
        familySize = 4;
        shopSize = 6;
        salary = 10;
        taxRate = 0.3;
        serviceCost = 2;
        houseBuildingCost = 100;
        commBuildingCost = 500;
        roadBuildingCost = 20;
    }

    //purpose: returns the width of the map
    public int getWidth()
    {
        return mapWidth;
    }

    //purpose: returns the height of the map
    public int getHeight()
    {
        return mapHeight;
    }

    //purpose: returns the initial amount of money
    public int getInitialMoney()
    {
        return initialMoney;
    }

    //purpose: returns the cost of building a house/residential building
    public int getHouseCost()
    {
        return houseBuildingCost;
    }

    //purpose: returns the cost of building commercial building
    public int getCommCost()
    {
        return commBuildingCost;
    }

    //purpose: returns the cost of building a road
    public int getRoadBuildingCost()
    {
        return roadBuildingCost;
    }

    /*purpose: methods for returning:
                    familysize - number of people per residential structure
                    shopsize - size of commercial structures
                    servicecost - cost to service the town
                    tax rate - tax rate of the town
                    salary - salary of the town
    */

    public int getFamilySize() { return familySize; }

    public int getShopSize() { return shopSize; }

    public int getServiceCost() { return serviceCost; }

    public double getTaxRate() { return taxRate; }

    public int getSalary() { return salary; }

    //purpose: methods for setting the width and height of the map elements array

    public void setWidth(int newWidth)
    {
        this.mapWidth = newWidth;
    }

    public void setHeight(int newHeight)
    {
        this.mapHeight = newHeight;
    }

    //purpose: method setting the initial amount of money for the game

    public void setInitialMoney(int newMoney)
    {
        this.initialMoney = newMoney;
    }


    //purpose: method for load settings information from the app database
    public void load(SQLiteDatabase db)
    {
        this.db = db;//set reference to the app's database

        //initialise a cursor to get the setting from the database
        GameDbCursor cursor = new GameDbCursor(db.query(SettingsTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null)
        );

        cursor.moveToFirst();//move to the stored settings if there are any
        if(!cursor.isAfterLast())//if there is settings stored then set the settings to these
                                 //stored settings
        {
            Settings newSettings = cursor.getSettingsData();//retrieve stored settings
            //set width, height of the array and initial money of the game to stored information
            this.setWidth(newSettings.getWidth());
            this.setHeight(newSettings.getHeight());
            this.setInitialMoney(newSettings.getInitialMoney());
        }
        else//if there was no settings stored then store the default settings in the
            //database
        {
            add(this);
        }
        cursor.close();
    }

    //purpose: adding settings information to the database when it is being added for the first time
    public void add(Settings newSettings)
    {
        //create content value for storing value to be put into the database
        ContentValues cv = new ContentValues();
        //add information to be inserted into the database into the content value
        cv.put(SettingsTable.Cols.SETTINGS_ID, "1");
        cv.put(SettingsTable.Cols.MAP_WIDTH, newSettings.getWidth());
        cv.put(SettingsTable.Cols.MAP_HEIGHT, newSettings.getHeight());
        cv.put(SettingsTable.Cols.INITIAL_MONEY, newSettings.getInitialMoney());
        //insert content value into the database
        db.insert(SettingsTable.NAME, null, cv);

    }

    //purpose: if there is a change to the settings of the game update the database of the change
    public void update(Settings newSettings)
    {
        //store values to be updated in the content value
        ContentValues cv = new ContentValues();
        //put new values into the content value
        cv.put(SettingsTable.Cols.MAP_WIDTH, newSettings.getWidth());
        cv.put(SettingsTable.Cols.MAP_HEIGHT, newSettings.getHeight());
        cv.put(SettingsTable.Cols.INITIAL_MONEY, newSettings.getInitialMoney());
        //where the settings has id 1 in the database
        String[] whereValue = { "1" };
        //update database where id is 1
        db.update(SettingsTable.NAME, cv,
                SettingsTable.Cols.SETTINGS_ID + " = ?", whereValue);
    }

}

