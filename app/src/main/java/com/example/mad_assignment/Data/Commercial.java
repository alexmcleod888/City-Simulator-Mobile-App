package com.example.mad_assignment.Data;

/*Author: Alex McLeod
  Purpose: model class for storing, retrieving and modifying the information for each commercial
           building. It inherits from the structure class.
  Date Modified: 15/10/2019
 */
public class Commercial extends Structure
{
    //Default label for each Commercial object
    private static final String COMM_LABEL = "Commercial";

    //declare and initialise the cost and number of commercial buildings to 0
    private static int cost = 0;
    private static int numCommercial = 0;

    //constructor for the commercial class allowing them to set the id of the image associated
    //with it and its name
    public Commercial(int imageId, String name)
    {
        super(imageId, COMM_LABEL);
        //if no name is given then set the name to the same as the label
        if(name == null) {
            super.setName(COMM_LABEL);
        }
        //otherwise set as the given name
        else
        {
            super.setName(name);
        }
    }

    //purpose: retrieve the cost of a commercial building
    @Override
    public int getCost()
    {
        return cost;
    }

    //purpose: returns the current number of commercial buildings
    public static int getNumCommercial()
    {
        return numCommercial;
    }

    //purpose: method for checking the type of structure
    @Override
    public String getType()
    {
        return COMM_LABEL;
    }

    //purpose: set the cost of a commercial building
    public static void setCost(int newCost)
    {
        cost = newCost;
    }

    //purpose: allows us to set the current number of commercial buildings
    public static void setNumCommercial(int newNumCommercial)
    {
        numCommercial = newNumCommercial;
    }

    //purpose: allows us us to increase the number of commercial buildings
    @Override
    public void increaseTotal()
    {
        numCommercial += 1;
    }

    //purpose: method that returns false. Used for when the user wants to check if a particular
    //structure is a road
    @Override
    public boolean isARoad()
    {
        return false;
    }

    //purpose: method for decreasing the number of commercial buildings
    @Override
    public void decreaseTotal() { numCommercial -= 1; }

    //method for updating the name of particular structure
 /*   @Override
    public void updateName()
    {
        //set the name to what the temp name is
        super.setName(super.getTempName());
        //once set, set temp name back to the default label
        super.setTempName(COMM_LABEL);
    }*/

    //purpose: method for creating a clone of this structure
    @Override
    public Structure cloneStructure()
    {
        //return a new Commercial structure with this current structures's image id and name
        return new Commercial(super.getImageId(), super.getName());
    }
}
