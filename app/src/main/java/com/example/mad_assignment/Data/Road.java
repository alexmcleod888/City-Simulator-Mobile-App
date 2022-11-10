package com.example.mad_assignment.Data;

/*Author: Alex McLeod
  Purpose: model class for storing, accessing and modifying Road object information
  Date Modified: 15/10/2019
 */
public class Road extends Structure
{
    //default label for each road structure
    private static final String ROAD_LABEL = "Road";
    //declare and set the cost and number of roads initially to 0
    private static int cost = 0;
    private static int numRoads = 0;

    //constructor for setting the image id number and name of the road
    public Road(int imageId, String name)
    {
        super(imageId, ROAD_LABEL);
        if(name == null) {//if no name is given just set the name to the default label
            super.setName(ROAD_LABEL);
        }
        else//otherwise set the name
        {
            super.setName(name);
        }
    }

    //purpose: getting the cost of a road structure
    @Override
    public int getCost()
    {
        return cost;
    }

    //setting the cost of a road structure
    public static void setCost(int newCost)
    {
        cost  = newCost;
    }

    //purpose: method for increasing the total number of roads
    @Override
    public void increaseTotal()
    {
        numRoads += 1;
    }

    //purpose: method for checking if this is a road. In this case it is so return true
    //used when checking if a structure object is a road
    @Override
    public boolean isARoad()
    {
        return true;
    }

    //purpose: decrease the total number of road structures on the map
    @Override
    public void decreaseTotal() { numRoads -= 1; }

    //purpose: method for returning the type of structure this is from a structure object
    @Override
    public String getType()
    {
        return ROAD_LABEL;
    }

    //purpose: creating a clone of this road structure
    @Override
    public Structure cloneStructure()
    {
        return new Road(super.getImageId(), super.getName());
    }
}
