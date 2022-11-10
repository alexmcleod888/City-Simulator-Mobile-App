package com.example.mad_assignment.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mad_assignment.Database.GameDatabaseSchema.SettingsTable;
import com.example.mad_assignment.Database.GameDatabaseSchema.GameDataTable;
import com.example.mad_assignment.Database.GameDatabaseSchema.MapElementTable;

/*Author: Alex McLeod
  Purpose: database helper that creates the different tables of the database
  Date Modified: 15/10/2019
 */

public class GameDbHelper extends SQLiteOpenHelper
{
    //database version number
    private static final int VERSION = 1;
    //name of the database
    private static final String DATABASE_NAME = "game.db";



    //constructor for creating the database helper
    public GameDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //purpose: upon creating the database helper create the different tables within the table
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //create settings table and its different columns
        db.execSQL("CREATE TABLE " + SettingsTable.NAME + "(" +
                SettingsTable.Cols.SETTINGS_ID + " INTEGER, "  +
                SettingsTable.Cols.MAP_WIDTH + " INTEGER, " +
                SettingsTable.Cols.MAP_HEIGHT + " INTEGER, " +
                SettingsTable.Cols.INITIAL_MONEY + " INTEGER)");

        //create game data table and its different columns
        db.execSQL("CREATE TABLE " + GameDataTable.NAME + "(" +
                GameDataTable.Cols.GAME_ID + " INTEGER, "  +
                GameDataTable.Cols.CURRENT_MONEY + " INTEGER, " +
                GameDataTable.Cols.GAME_TIME + " INTEGER, " +
                GameDataTable.Cols.NUM_RESIDENTIAL + " INTEGER, " +
                GameDataTable.Cols.NUM_COMMERCIAL + " INTEGER)");

        //create map element table and its different columns
        db.execSQL("CREATE TABLE " + MapElementTable.NAME + "(" +
                MapElementTable.Cols.IMAGE_ID + " INTEGER, " +
                MapElementTable.Cols.LABEL + " TEXT, " +
                MapElementTable.Cols.NAME + " TEXT, " +
                MapElementTable.Cols.ROW + " INTEGER, " +
                MapElementTable.Cols.COLUMN + " INTEGER)");

    }

    //purpose: method for upgrading the database. Not used in this case
    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2){}
}
