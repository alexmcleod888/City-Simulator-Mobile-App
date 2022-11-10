package com.example.mad_assignment.Data;

/*Author: Alex McLeod
  Purpose: model class for storing, accessing and modifying residential structure objects.
           It inherits from the structure class
  Date Modified: 15/10/2019
 */
public class Residential extends Structure
{
    //default label for each residential object
    private static final String RES_LABEL = "Residential";

    //declare and initialise the cost and number of residential structures to 0
    private static int cost = 0;
    private static int numResidential = 0;

    //constructor for creating a new residential object with a particular image number and name
    public Residential(int imageId, String name)
    {
        super(imageId, RES_LABEL);
        if(name == null)//if no number is given then set the name to the default label
        {
            super.setName(RES_LABEL);
        }
        else//otherwise if a name was given set it to the structure
        {
            super.setName(name);
        }
    }

    //purpose: method for get the cost of a residential structure
    @Override
    public int getCost()
    {
        return cost;
    }

    //purpose: method for setting the cost of a residential structure
    public static void setCost(int newCost)
    {
        cost = newCost;
    }

    //purpose: method for getting the number of residential structure currently on the map
    public static int getNumResidential()
    {
        return numResidential;
    }

    //purpose: method for setting the number of current residential structures on the map
    public static void setNumResidential(int newNumResidential)
    {
        numResidential = newNumResidential;
    }

    //purpose: method increasing the total number of residential structures
    @Override
    public void increaseTotal()
    {
        numResidential += 1;
    }

    //purpose: method that returns false as this structure is no a road. used to check whether a
    //structure object is a road
    @Override
    public boolean isARoad()
    {
        return false;
    }

    //purpose: method for decreasing the total number of residential structures on the map
    @Override
    public void decreaseTotal() { numResidential -= 1; }

    //purpose: method for getting the type of structure
    @Override
    public String getType()
    {
        return RES_LABEL;
    }

    //purpose: method for creating a clone of this residential object
    @Override
    public Structure cloneStructure()
    {
        return new Residential(super.getImageId(), super.getName());
    }

}
