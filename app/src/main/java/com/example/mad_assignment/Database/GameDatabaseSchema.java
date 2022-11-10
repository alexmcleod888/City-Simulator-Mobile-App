package com.example.mad_assignment.Database;

/*Author: Alex McLeod
  Purpose: class for holding the different table names within the database along with the
           names of the different columns within these tables
  Date Modified: 15/10/2019
 */
public class GameDatabaseSchema
{
    //Strings for the table that holds the settings information
    public static class SettingsTable
    {
        public static final String NAME = "settings";//name of settings table
        //name of the different columns of the settings table
        public static class Cols
        {
            public static final String SETTINGS_ID = "settings_id";
            public static final String MAP_WIDTH = "map_width";
            public static final String MAP_HEIGHT = "map_height";
            public static final String INITIAL_MONEY = "initial_money";
        }
    }

    //Strings for the table that holds the game data information
    public static class GameDataTable
    {
        public static final String NAME = "gameData";//name of the game data table
        //name of the different columns of the game data table
        public static class  Cols
        {
            public static final String GAME_ID = "game_id";
            public static final String CURRENT_MONEY = "current_money";
            public static final String GAME_TIME = "game_time";
            public static final String NUM_RESIDENTIAL = "num_residential";
            public static final String NUM_COMMERCIAL = "num_commercial";
        }
    }

    //Strings for the table that holds the Map Element information
    public static class MapElementTable
    {
        public static final String NAME = "mapElements";//name of the map elements table
        //name of the different columns of the Map Element table
        public static class Cols
        {
            public static final String IMAGE_ID = "image_id";
            public static final String LABEL = "label";
            public static final String NAME = "name";
            public static final String ROW = "rowNum";
            public static final String COLUMN = "columnNum";
        }
    }
}
