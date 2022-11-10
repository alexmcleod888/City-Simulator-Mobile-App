package com.example.mad_assignment.Database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.mad_assignment.Data.Commercial;
import com.example.mad_assignment.Data.GameData;
import com.example.mad_assignment.Data.MapElement;
import com.example.mad_assignment.Data.Residential;
import com.example.mad_assignment.Data.Road;
import com.example.mad_assignment.Data.Structure;
import com.example.mad_assignment.Database.GameDatabaseSchema.SettingsTable;
import com.example.mad_assignment.Database.GameDatabaseSchema.GameDataTable;
import com.example.mad_assignment.Database.GameDatabaseSchema.MapElementTable;
import com.example.mad_assignment.Data.Settings;

/*Author: Alex McLeod
  Purpose: Class for creating a cursor that allows us to return information from the database tables
  Date Modified: 15/10/2019
 */
public class GameDbCursor extends CursorWrapper
{
    //constructor for creating new a cursor
    public GameDbCursor(Cursor cursor)
    {
        super(cursor);
    }

    //purpose: method for getting the setting information from the database
    public Settings getSettingsData()
    {
        //get data
        int mapWidth = getInt(getColumnIndex(SettingsTable.Cols.MAP_WIDTH));
        int mapHeight = getInt(getColumnIndex(SettingsTable.Cols.MAP_HEIGHT));
        int initialMoney = getInt(getColumnIndex(SettingsTable.Cols.INITIAL_MONEY));
        //return a new settings object created using the database information
        return new Settings(mapWidth, mapHeight, initialMoney);
    }

    //purpose: method for getting the game data information from the database
    public GameData getGameData(SQLiteDatabase db)
    {
        //get data
        int currentMoney = getInt(getColumnIndex(GameDataTable.Cols.CURRENT_MONEY));
        int gameTime = getInt(getColumnIndex(GameDataTable.Cols.GAME_TIME));
        int numResidential = getInt(getColumnIndex(GameDataTable.Cols.NUM_RESIDENTIAL));
        int numCommercial = getInt(getColumnIndex(GameDataTable.Cols.NUM_COMMERCIAL));
        //return a new GameData object create using the data information
        return new GameData(db, currentMoney, gameTime, numResidential, numCommercial);
    }

    //purpose: method for getting a map element from the database
    public void addMapElement(MapElement[][] map)
    {
        //used to store a reference to the structure that will be part of the map element
        Structure newStructure;

        //get data
        int imageId = getInt(getColumnIndex(MapElementTable.Cols.IMAGE_ID));
        String label = getString(getColumnIndex(MapElementTable.Cols.LABEL));
        String name = getString(getColumnIndex(MapElementTable.Cols.NAME));
        int row = getInt(getColumnIndex(MapElementTable.Cols.ROW));
        int column = getInt(getColumnIndex(MapElementTable.Cols.COLUMN));

        //if the label of the map element is 'road' create a Road structure to store in the map
        //element
        if(label.equals("Road")) {
            newStructure = new Road(imageId, name);
        }
        //if the label is 'residential' create a Residential structure to store in the map element
        else if(label.equals("Residential"))
        {
            newStructure = new Residential(imageId, name);
        }
        //otherwise the label must be 'Commercial', hence create a Road structure to store in the
        //map element
        else
        {
            newStructure = new Commercial(imageId, name);
        }

        //create the new map element based on the data taken from the database from a particular row
        MapElement newMapElement = new MapElement(newStructure, null, row, column);
        //add reference of the map element from the database to the imported 2D map array
        map[row][column] = newMapElement;

    }

    //purpose: method for getting a map element from the database
    public MapElement getMapElement()
    {
        //used to store a reference to the structure that will be part of the map element
        Structure newStructure;

        //get data
        int imageId = getInt(getColumnIndex(MapElementTable.Cols.IMAGE_ID));
        String label = getString(getColumnIndex(MapElementTable.Cols.LABEL));
        String name = getString(getColumnIndex(MapElementTable.Cols.NAME));
        int row = getInt(getColumnIndex(MapElementTable.Cols.ROW));
        int column = getInt(getColumnIndex(MapElementTable.Cols.COLUMN));

        //if the label of the map element is 'road' create a Road structure to store in the map
        //element
        if(label.equals("Road")) {
            newStructure = new Road(imageId, name);
        }
        //if the label is 'residential' create a Residential structure to store in the map element
        else if(label.equals("Residential"))
        {
            newStructure = new Residential(imageId, name);
        }
        //otherwise the label must be 'Commercial', hence create a Road structure to store in the
        //map element
        else
        {
            newStructure = new Commercial(imageId, name);
        }

        //create the new map element based on the data taken from the database from a particular row
        MapElement newMapElement = new MapElement(newStructure, null, row, column);
        //add reference of the map element from the database to the imported 2D map array
        return newMapElement;

    }
}
