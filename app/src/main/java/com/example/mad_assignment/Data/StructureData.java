package com.example.mad_assignment.Data;

import com.example.mad_assignment.R;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/*Author: David Cooper, modified by Alex McLeod
  Purpose: class that holds a list of drawable references for the different structure images.
           It also holds a list of different structures that can be selected. This class
           allows us to access this list.
           The add and remove methods have been deleted.
  Date Modified: 13/10/2019
  Reference: Worksheet 3, Mobile App Development 2019, Curtin University
 */
public class StructureData implements Serializable
{
    //list of different drawable integers that represent the different images for the different
    //structures
    public static final int[] DRAWABLES = {
            0, // No structure
            R.drawable.ic_building1, R.drawable.ic_building2, R.drawable.ic_building3,
            R.drawable.ic_building4, R.drawable.ic_building5, R.drawable.ic_building6,
            R.drawable.ic_building7, R.drawable.ic_building8,
            R.drawable.ic_road_ns, R.drawable.ic_road_ew, R.drawable.ic_road_nsew,
            R.drawable.ic_road_ne, R.drawable.ic_road_nw, R.drawable.ic_road_se, R.drawable.ic_road_sw,
            R.drawable.ic_road_n, R.drawable.ic_road_e, R.drawable.ic_road_s, R.drawable.ic_road_w,
            R.drawable.ic_road_nse, R.drawable.ic_road_nsw, R.drawable.ic_road_new, R.drawable.ic_road_sew,
    };

    //list of possible structure objects that can be created, each element creates a
    //a new structure
    private List<Structure> structureList = Arrays.asList(new Structure[] {
            new Residential(R.drawable.ic_building1, null),
            new Commercial(R.drawable.ic_building2, null),
            new Road(R.drawable.ic_road_ns, null),
            new Road(R.drawable.ic_road_e, null),
            new Road(R.drawable.ic_road_nsew, null),
            new Road(R.drawable.ic_road_ne, null),
            new Road(R.drawable.ic_road_nw, null),
            new Road(R.drawable.ic_road_se, null),
            new Road(R.drawable.ic_road_sw, null),
            new Road(R.drawable.ic_road_n, null),
            new Road(R.drawable.ic_road_e, null),
            new Road(R.drawable.ic_road_s, null),
            new Road(R.drawable.ic_road_w, null),
            new Road(R.drawable.ic_road_nse,null),
            new Road(R.drawable.ic_road_nsw, null),
            new Road(R.drawable.ic_road_new, null),
            new Road(R.drawable.ic_road_sew, null),
    });

    //purpose: set instance of structure data to null by default. structure data is NULL if it hasn't
    //been created yet
    private static StructureData instance = null;

    //purpose: returns the instance of the StructureData
    public static StructureData get()
    {
        if(instance == null)//if the structureData instance hasn't been created yet create it
        {
            instance = new StructureData();
        }
        return instance;//if it has already been created just return it
    }

    //purpose: default constructor
    protected StructureData() {}

    //purpose: returns a particular structure from the list of possible structure based on an
    //imported index
    public Structure get(int i)
    {
        return structureList.get(i);
    }

    //purpose: returns the length of the list of possible structures
    public int size()
    {
        return structureList.size();
    }

}

