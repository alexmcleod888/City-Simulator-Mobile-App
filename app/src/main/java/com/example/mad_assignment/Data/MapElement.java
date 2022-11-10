package com.example.mad_assignment.Data;

import android.graphics.Bitmap;

/*Author: Alex McLeod
  Purpose: Model class for storing, getting and modifying the information for a
  particular map element
  Date Modified: 15/10/2019
 */
public class MapElement {

    //Map element data
    private Structure structure;
    private Bitmap image;
    private String ownerName;
    private int row;
    private int column;

    //constructor for a new map element that is given a particular row and column number which it
    //stores
    public MapElement(int row, int column)
    {
        this.structure = null;
        this.image = null;
        this.row = row;
        this.column = column;
    }

    //constructor that is used to create a new map element based on the information
    //obtained from the app's database
    public MapElement(Structure structure, Bitmap image, int row, int column)
    {
        this.structure = structure;
        this.image = image;
        this.ownerName = ownerName;
        this.row = row;
        this.column = column;
    }

    //purpose: method for returning the structure stored within the map element
    public Structure getStructure()
    {
        return structure;
    }

    ///purpose: method for returning the image associated with this map element
    public Bitmap getImage() { return image; }

    //purpose: method returning the map elements row number
    public int getRow()
    {
        return row;
    }

    //purpose: method for returning the map elements column number
    public int getColumn()
    {
        return column;
    }

    //purpose: method for setting the structure for this map element
    public void setStructure(Structure structure)
    {
        this.structure = structure;
    }

    //purpose: method for setting the image for this map element
    public void setImage(Bitmap newImage)
    {
        this.image = newImage;
    }

    //purpose: method for checking if the structure stored within this map element is a road
    //used when building residential and commercial structures to check if a road is next to them
    public boolean isARoad()
    {
        boolean isARoad;
        if(structure == null)//if there is not structure stored then it isnt a road
        {
            isARoad = false;
        }
        else
        {
            isARoad = structure.isARoad();//check structure
        }
        return isARoad;
    }
}
