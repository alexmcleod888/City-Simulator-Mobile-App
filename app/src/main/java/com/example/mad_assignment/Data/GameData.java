package com.example.mad_assignment.Data;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mad_assignment.Database.GameDatabaseSchema.MapElementTable;
import com.example.mad_assignment.Database.GameDatabaseSchema.GameDataTable;
import com.example.mad_assignment.Database.GameDbCursor;
import com.example.mad_assignment.Database.GameDbHelper;

/*Author: Alex McLeod
  Purpose: model class for storing, accessing and modifying game data. Including the current game
           settings and the app's database
  Date Modified: 15/10/2019
 */

public class GameData {
    //reference to the settings of the game
    private Settings settings;
    //reference to the 2D array of map elements
    private MapElement[][] map;

    // variables which hold the current game stats and different game setting values
    private int money;
    private int gameTime;
    private int population;
    private double employmentRate;
    private int currentIncome;
    private int familySize;
    private int shopSize;
    private int nResidential;
    private int nCommercial;
    private double taxRate;
    private int serviceCost;
    private int salary;

    /* variable for holding a reference to this game data object. It allows us to check whether
       an instance of GameData has been created. In the get method if this is the first time game
       data is being retrieved then instance will be set to null and a new GameData object will
       be created. Otherwise the get method returns the current GameData.*/
    private static GameData instance = null;

    //reference to the app's database
    private SQLiteDatabase db;

    //purpose: method for creating a instance of GameData if it hasnt been created yet, otherwise
    //it just returns the current instance of GameData/
    public static GameData get(Activity activity)
    {
        //if instance is null then create new GameData object
        if(instance == null)
        {
            instance = new GameData();
            instance.load(activity);
        }
        return instance;
    }

    //constructor for creating a new GameData object
    public GameData()
    {
        //upon creation also create a settings object and store a reference to it
        settings = new Settings();

        //set the cost of each type of structure to what the settings object states there cost is
        Residential.setCost(settings.getHouseCost());
        Commercial.setCost(settings.getCommCost());
        Road.setCost(settings.getRoadBuildingCost());
        //create a new MapElement array with height and width given by the settings object
        //storing a reference to this array
        map = new MapElement[settings.getHeight()][settings.getWidth()];
        //generate the map elements for this array as default values
        generateGrid(map);

        //set the values of particular stats as given by the settings object
        shopSize = settings.getShopSize();
        familySize = settings.getFamilySize();
        money = settings.getInitialMoney();
        salary = settings.getSalary();
        taxRate = settings.getTaxRate();
        serviceCost = settings.getServiceCost();

        //set set the values that change over time to 0 initially
        gameTime = 0;
        population = 0;
        currentIncome = 0;
        employmentRate = 0.0;
        this.nResidential = 0;
        Residential.setNumResidential(nResidential);
        this.nCommercial = 0;
        Commercial.setNumCommercial(nCommercial);
    }

    //constructor for creating a GameData object based on the information stored within the
    //app's database
    public GameData(SQLiteDatabase db, int currentMoney, int gameTime, int numResidential,
                    int numCommercial)
    {
        //create a new settings object
        settings = new Settings();
        //if there is already a settings object stored within the games database than load it
        settings.load(db);

        //set the cost of the different games structures based on the settings object
        Residential.setCost(settings.getHouseCost());
        Commercial.setCost(settings.getCommCost());
        Road.setCost(settings.getRoadBuildingCost());

        //create map element array
        map = new MapElement[settings.getHeight()][settings.getWidth()];
        //generate default values for map elements
        generateGrid(map);

        //set values of particular stat's based on setting object's values
        shopSize = settings.getShopSize();
        familySize = settings.getFamilySize();
        money = settings.getInitialMoney();
        salary = settings.getSalary();
        taxRate = settings.getTaxRate();
        serviceCost = settings.getServiceCost();

        //set the values of changing stats to whatever was loaded in from the database
        this.money = currentMoney;
        this.gameTime = gameTime;
        this.nResidential = numResidential;
        Residential.setNumResidential(nResidential);
        this.nCommercial = numCommercial;
        Commercial.setNumCommercial(nCommercial);
        this.population = familySize * nResidential;
        this.employmentRate = Math.min(1, (double) nCommercial * ((double) shopSize) / ((double) population));
        this.currentIncome = (int) (population * (employmentRate * salary * taxRate - serviceCost));
    }

    //purpose: returns a reference to the settings object
    public Settings getSettings()
    {
        return settings;
    }

    //purpose: method for returning the width of the current map element array
    public int getWidth()
    {
        return settings.getWidth();
    }

    //purpose: method for return the height of the current map element
    public int getHeight()
    {
        return settings.getHeight();
    }

    //purpose: method for creating default elements for each index of the map element array
    private void generateGrid(MapElement[][] grid)
    {
        System.out.println(settings.getHeight());
        System.out.println(settings.getWidth());
        //goes through each row and column
        for(int i = 0; i < settings.getHeight(); i++)
        {
            for(int j = 0; j < settings.getWidth(); j++)
            {
                //creates a new default map element storing its coordinates within the array
                //within the object itself
                grid[i][j] = new MapElement(i, j);
            }
        }

    }

    //purpose: method for returning a particular map element based on an imported row and column
    public MapElement getMapElement(int row, int column)
    {
        return map[row][column];
    }

    //purpose: method for returning the current amount of money the user has
    public int getCurrentMoney()
    {
        return money;
    }

    //purpose: method for returning the current amount of money earned by the user oer increase in
    //time
    public int getIncome() { return currentIncome; }

    //purpose: method for getting current number of residential structures
    public int getNumResidential()
    {
        return nResidential;
    }

    //purpose: method for returning the current number of commercial structures
    public int getNumCommercial()
    {
        return nCommercial;
    }

    //purpose: method for setting the number of residential houses on the map
    public void setNumResidential(int numResidential)
    {
        nResidential = numResidential;
    }

    //purpose: method for settings the number of commercial structures on the map
    public void setNumCommercial(int numCommercial)
    {
        nCommercial = numCommercial;
    }

    //purpose: method for setting the reference to the settings object
    public void setSettings(Settings settings)
    {
        this.settings = settings;
    }

    //purpose:method for updating the amount of money the user has from the
    //database
    public void setMoney(int money)
    {
        this.money = money;
    }

    //purpose: method for setting the users current amount of money
    //used after game data has been loaded from database
    public void setCurrentMoney(int money)
    {
        this.money = money;
        //if the current amount of money has changed update the game's database
        updateGameData(this);
    }

    //purpose: for settings the current time of the game
    public void setCurrentTime(int time) { this.gameTime = time; }

    //purpose: method for setting the current width of the map
    public void setWidth(int newWidth)
    {
        settings.setWidth(newWidth);
    }

    //purpose: method for setting the current height of map
    public void setHeight(int newHeight)
    {
        settings.setHeight(newHeight);
    }

    //purpose: method for setting the initial amount of money the user starts with
    public void setInitialMoney(int newMoney)
    {
        money = newMoney;
        //also update the settings object
        settings.setInitialMoney(newMoney);
    }

    //purpose: method for increasing the game time
    public void increaseTime()
    {
        gameTime += 1;
        //update database that current game time has changed
        updateGameData(this);
    }

    //purpose: for returning the current game time
    public int getTime()
    {
        return gameTime;
    }

    //purpose: method for setting the population of game
    public void setPopulation(int population) { this.population = population; }

    /*public MapElement[][] getGameMap()
    {
        return map;
    }*/

    /*public void setGameMap(MapElement[][] newMap)
    {
        for(int i = 0; i < settings.getHeight(); i++)
        {
            for(int j = 0; j < settings.getWidth(); j++)
            {
                map[i][j] = newMap[i][j];
            }
        }
    }*/

    //purpose: method for calculating the population of the game
    public void setPopulation()
    {
        //calculation
        population = familySize * nResidential;
        //update the database to the population update
        updateGameData(this);
    }

    //purpose: method for setting the current income
    public void setCurrentIncome(int currentIncome) { this.currentIncome = currentIncome; }

    //purpose: method for retrieving the current population
    public int getPopulation()
    {
        return population;
    }

    //purpose: method for calculating and setting the current employment rate
    public void setEmploymentRate()
    {
        //if the population isnt 0 then we can calculate the population
        if(population != 0)
        {
            //calculate
            employmentRate = Math.min(1, (double) nCommercial * ((double) shopSize) / ((double) population));
            //update the database of the employment rate change
            updateGameData(this);
        }
    }

    //purpose: set the employment rate
    public void setEmploymentRate(double employmentRate)
    {
        this.employmentRate = employmentRate;
    }


    //purpose: to get the employment rate as a percentage
    public double getEmploymentRatePercentage()
    {
        return Math.round(employmentRate * 10000.0) / 100.0;
    }

    //purpose: to return the employment rate
    public double getEmploymentRate() { return employmentRate; };

    //purpose: method for updating the number of residential and commercial structures
    public void updateNumStructures()
    {
        //update the number of residential structures
        updateResidential();
        //update the number of commercial structures
        updateCommercial();
        //update database to the change in the number of structures
        updateGameData(this);
    }

    //purpose: update the number of residential buildings
    private void updateResidential()
    {
        nResidential = Residential.getNumResidential();
        //set the new population as the number of residential buildings affects the population
        setPopulation();
    }

    //purpose: update the number of commercial buildings
    private void updateCommercial()
    {
        nCommercial = Commercial.getNumCommercial();
        //set the new employment rate as the number of commercial buildings affects the employment
        //rate
        setEmploymentRate();
    }

    //purpose: method for updating and calculating the currently earned income per increase in time
    public void updateIncome() {
        currentIncome = (int) (population * (employmentRate * salary * taxRate - serviceCost));
        //update the database to the change in the current income
        updateGameData(this);
    }

    //purpose: if the user has changed the settings of the game then update databases
    //and gameData's information. It also resets the game map removing any existing structures
    public void updateSettings()
    {
        //update database to the change in settings
        settings.update(settings);
        //create a new map array with new height and width if they have changed
        map = new MapElement[settings.getHeight()][settings.getWidth()];
        //generate default values for each map element
        generateGrid(map);

        GameDbCursor cursor = new GameDbCursor(db.query(MapElementTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null)
        );

        try//if there is elements in the table then move to the first
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())//while we havn't gone past the last element
            {
                removeMapElement(cursor.getMapElement());//import the map element array and add the map element
                //to it
                cursor.moveToNext();//move to the next map element that is stored
            }
        }
        finally//no matter wht close the cursor after checking database
        {
            cursor.close();
        }


        //set the starting amount of money to the initial amount of money given by the user
        money = settings.getInitialMoney();
        //set everything else back to 0
        gameTime = 0;
        population = 0;
        currentIncome = 0;
        employmentRate = 0.0;
        this.nResidential = 0;
        Residential.setNumResidential(nResidential);
        this.nCommercial = 0;
        Commercial.setNumCommercial(nCommercial);

        updateGameData(this);

    }

    //purpose: method for loading the game data from a database
    public void load(Context context)
    {
        //initialise a new database
        this.db = new GameDbHelper(
                context.getApplicationContext()
        ).getWritableDatabase();

        //retrieve a cursor for the game data table to retrieve information
        GameDbCursor cursor = new GameDbCursor(db.query(GameDataTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null)
        );

        settings = new Settings();
        settings.load(db);
        /*this.height = settings.getHeight();
        this.width = settings.getWidth();*/
        map = new MapElement[settings.getHeight()][settings.getWidth()];
        //move to the first value in the table
        cursor.moveToFirst();
        //while the cursor has not gone past the last value in the table keep retrieving data
        if(!cursor.isAfterLast())
        {
            //get a reference to the stored game data object from the database
            GameData newGameData = cursor.getGameData(db);

            //after retrieving the reference set the game data object's field to be
            //the same as the stored game data from the database
            setSettings(newGameData.getSettings());
            System.out.println("current Money: " + newGameData.getCurrentMoney());
            setMoney(newGameData.getCurrentMoney());
            setCurrentTime(newGameData.getTime());
            System.out.println("current population: " + newGameData.getPopulation());
            setPopulation(newGameData.getPopulation());
            System.out.println("current employmentRate: " + newGameData.getEmploymentRate());
            setEmploymentRate(newGameData.getEmploymentRate());
            setCurrentIncome(newGameData.getIncome());
            setNumResidential(newGameData.getNumResidential());
            Residential.setNumResidential(newGameData.getNumResidential());
            setNumCommercial(newGameData.getNumCommercial());
            Commercial.setNumCommercial(newGameData.getNumCommercial());

            //generate a grid with default values at each index of the array
            generateGrid(map);
            //load the any map elements that were stored in the database
            loadMap(db);
        }
        else// if there is no currently stored game data object in the database then store one
        {
            add(this);
        }
        cursor.close();//close cursor
    }

    //purpose: method for loading map elements from the database
    public void loadMap(SQLiteDatabase db)
    {
        //create a cursor to get information from map element table
        GameDbCursor cursor = new GameDbCursor(db.query(MapElementTable.NAME,
                null,
                null,
                null,
                null,
                null,
                null)
        );

        try//if there is elements in the table then move to the first
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())//while we havn't gone past the last element
            {
                cursor.addMapElement(map);//import the map element array and add the map element
                                          //to it
                cursor.moveToNext();//move to the next map element that is stored
            }
        }
        finally//no matter wht close the cursor after checking database
        {
            cursor.close();
        }

    }

    //purpose: method for adding a GameData object to the database
    public void add(GameData newGameData)
    {
        //create a new ContentValue to store the information for a particular gameData object
        //which will be added to the database
        ContentValues cv = new ContentValues();
        //add information to content value
        cv.put(GameDataTable.Cols.GAME_ID, "1");
        cv.put(GameDataTable.Cols.CURRENT_MONEY, newGameData.getCurrentMoney());
        cv.put(GameDataTable.Cols.GAME_TIME, newGameData.getHeight());
        cv.put(GameDataTable.Cols.NUM_RESIDENTIAL, newGameData.getNumResidential());
        cv.put(GameDataTable.Cols.NUM_COMMERCIAL, newGameData.getNumCommercial());
        //insert content value into the game data database
        db.insert(GameDataTable.NAME, null, cv);

    }

    //purpose: add a new map element to the database
    public void addMapElement(MapElement newMapElement)
    {
        //get the structure from the map element to add
        Structure structure = newMapElement.getStructure();

        //create content view to hold the data to go into the database
        ContentValues cv = new ContentValues();
        cv.put(MapElementTable.Cols.IMAGE_ID, structure.getImageId());
        cv.put(MapElementTable.Cols.LABEL, structure.getLabel());
        cv.put(MapElementTable.Cols.NAME, structure.getName());
        cv.put(MapElementTable.Cols.ROW, newMapElement.getRow());
        cv.put(MapElementTable.Cols.COLUMN, newMapElement.getColumn());
        //insert the map element into the map element table
        db.insert(MapElementTable.NAME, null, cv);
    }

    //purpose: method for updating the game data stored within the database
    public void updateGameData(GameData newGameData)
    {
        //content view to hold the data to go into the database
        ContentValues cv = new ContentValues();
        cv.put(GameDataTable.Cols.CURRENT_MONEY, newGameData.getCurrentMoney());
        cv.put(GameDataTable.Cols.GAME_TIME, newGameData.getTime());
        cv.put(GameDataTable.Cols.NUM_RESIDENTIAL, newGameData.getNumResidential());
        cv.put(GameDataTable.Cols.NUM_COMMERCIAL, newGameData.getNumCommercial());
        //where the gameData has id 1 in the database
        String[] whereValue = { "1" };
        //update the gameData object within the database to the new gameData object
        db.update(GameDataTable.NAME, cv,
                GameDataTable.Cols.GAME_ID + " = ?", whereValue);
    }


    public void updateMapElements(MapElement newMapElement)
    {
        Structure structure = newMapElement.getStructure();

        ContentValues cv = new ContentValues();
        cv.put(MapElementTable.Cols.IMAGE_ID, structure.getImageId());
        cv.put(MapElementTable.Cols.LABEL, structure.getLabel());
        cv.put(MapElementTable.Cols.NAME, structure.getName());
        cv.put(MapElementTable.Cols.ROW, newMapElement.getRow());
        cv.put(MapElementTable.Cols.COLUMN, newMapElement.getColumn());
        String[] whereValue = { String.valueOf(newMapElement.getRow()),
                                String.valueOf(newMapElement.getColumn())};
        db.update(MapElementTable.NAME, cv, MapElementTable.Cols.ROW + " = ? AND " +
                  MapElementTable.Cols.COLUMN + " = ?", whereValue);
    }

    //purpose: method for removing a map element from the database
    public void removeGameData(GameData gameData)
    {
        //where value holds the particular row and column number of the element to remove from
        //the database
        String[] whereValue = { "1" };
        //remove this map element with this particular row and column number
        db.delete(GameDataTable.NAME,
                GameDataTable.Cols.GAME_ID + " = ? ", whereValue);


    }

    //purpose: method for removing a map element from the database
    public void removeMapElement(MapElement mapElement)
    {
        //where value holds the particular row and column number of the element to remove from
        //the database
        String[] whereValue = { String.valueOf(mapElement.getRow()),
                String.valueOf(mapElement.getColumn())};
        //remove this map element with this particular row and column number
        db.delete(MapElementTable.NAME,
                MapElementTable.Cols.ROW + " = ? AND " +
                        MapElementTable.Cols.COLUMN + " = ?", whereValue);
    }
}
