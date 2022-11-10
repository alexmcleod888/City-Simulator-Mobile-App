package com.example.mad_assignment.Data;

import java.io.Serializable;

/* Author: Alex McLeod
   Purpose: Represents a possible structure to be placed on the map. A structure simply contains a drawable
            int reference, and a string label to be shown in the Selector.
   Date Modified: 15/10/2019
  */
public abstract class Structure implements Serializable
{
    //drawable id of the structure
    private final int imageId;
    //label of the structure
    private final String label;
    //name of the structure
    private String name;
    //static variable which is used to temporarily hold the name of a structure
    private static String tempName = null;

    //constructor used to create a new structure
    public Structure(int imageId, String label)
    {
        this.imageId = imageId;
        this.label = label;
    }

    //constructor used to create a new structure based on a imported structure
    public Structure(Structure newStructure)
    {
        this.imageId = newStructure.getImageId();
        this.label = newStructure.getLabel();
        this.name = newStructure.getName();
    }

    //purpose: return drawable id of structure image
    public int getImageId()
    {
        return imageId;
    }

    //purpose: return the label of structure
    public String getLabel() { return label; }

    //purpose: used to set the name of the structure
    public void setName(String name)
    {
        this.name = name;
    }

    //used to get the name of structure
    public String getName()
    {
        return name;
    }

    //purpose: used to get the cost of a particular structure depending on what structure it is
    public abstract int getCost();

    //purpose: used to increase the total of a particular type of structure
    public abstract void increaseTotal();

    //purpose: used to check if the structure is a road
    public abstract boolean isARoad();

    //purpose: used to decrease the total number of a particular structure
    public abstract void decreaseTotal();

    //purpose: used to get the type of a structure e.g. residential, commercial, road
    public abstract String getType();

    //purpose: used to clone a particular constructor returning a copy of a structure
    public abstract Structure cloneStructure();

    //purpose: sets the value of the temporary name
    public static void setTempName(String newTempName)
    {
        tempName = newTempName;
    }

}
