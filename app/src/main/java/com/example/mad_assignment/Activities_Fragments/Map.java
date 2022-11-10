package com.example.mad_assignment.Activities_Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mad_assignment.Data.GameData;
import com.example.mad_assignment.Data.MapElement;
import com.example.mad_assignment.R;
import com.example.mad_assignment.Data.Structure;

import static android.app.Activity.RESULT_OK;

/*Author: Alex McLeod
  Purpose: Fragment for holding a recycler view using a grid layout that displays the different structures
           on the map. It is where the user can build new structures, demolish structures
           and access the details of each structure
  Date Modified: 14/10/2019
 */
public class Map extends Fragment
{
    // variable for storing a reference to the Selector fragment
    private Selector selectorFragment = null;
    // variable for holding the map adapter
    private MapAdapter mapAdapter;
    // variable for holding a reference to the gameData object, containing all current details
    // of the game and the game settings
    private GameData gameData;
    // integer key for retrieving the return intent
    private static final int REQUEST_CODE_MAPVHOLDER = 0;

    // variables that hold temporary data to be used to set information from return intent
    private Structure tempStructure = null;
    private MapElement tempMapElement = null;
    private ImageView tempStructureView = null;
    private int tempAdapterPosition = 0;


    public Map() {
        // Required empty public constructor
    }

    // purpose: method that initiates recycler view grid layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // set the game data reference
        gameData = GameData.get(getActivity());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // retrieve the recycler view from the xml layout
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.mapRecyclerView);
        // set the information for the new recycler view
        // set as a Grid layout
        rv.setLayoutManager(new GridLayoutManager(getActivity(), gameData.getHeight(), GridLayoutManager.HORIZONTAL,false));

        //create Adapter
        mapAdapter = new Map.MapAdapter(gameData);
        rv.setAdapter(mapAdapter);

        return view;
    }

    //purpose: method for setting the reference to the selector fragment
    public void setSelectorFragment(Selector selectorFragment)
    {
        this.selectorFragment = selectorFragment;
    }

    //purpose: inner private adapter class for the Map fragment
    private class MapAdapter extends RecyclerView.Adapter<MapAdapter.MapVHolder> {
        // reference to the GameData object
        private GameData data;

        //constructor for map adapter where reference to GameData is set
        public MapAdapter(GameData data) {
            this.data = data;
        }

        //purpose: method for retrieving the total number of elements in the map
        @Override
        public int getItemCount() {
            return (data.getHeight() * data.getWidth());

        }

        //purpose: create a new viewHolder when the recycler needs one
        @Override
        public MapVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new MapVHolder(li, parent);
        }

        //purpose: method called when recycler view needs to update a ViewHolder to display
        //         new data
        @Override
        public void onBindViewHolder(MapVHolder vh, int index) {
            MapElement upMapElement, downMapElement, leftMapElement, rightMapElement;

            //references to map elements on each side of the current map element
            //used by the current map element if it is a residential or commercial structure
            //to check if there is a road near next to it
            upMapElement = null;
            downMapElement = null;
            leftMapElement = null;
            rightMapElement = null;

            // get row and column of the current map element
            int row = index % data.getHeight();
            int col = index / data.getHeight();

            // get row and column of the map element above the current element
            if(index - 1 >= 0) {
                int row1 = (index - 1) % data.getHeight();
                int col1 = (index - 1) / data.getHeight();
                upMapElement = data.getMapElement(row1, col1);
            }

            // get row and column of the map element below the current element
            if(index + 1 < getItemCount() - 1) {
                int row2 = (index + 1) % data.getHeight();
                int col2 = (index + 1) / data.getHeight();
                downMapElement = data.getMapElement(row2, col2);
            }

            // get row and column of the map element to the left of the current element
            int row3 = (index) % data.getHeight();
            int col3 = ((index) / data.getHeight() - 1);
            if(col3 >= 0) {
                leftMapElement = data.getMapElement(row3, col3);
            }

            // get row and column of the map element to the right of the current element
            int row4 = (index) % data.getHeight();
            int col4 = ((index) / data.getHeight() + 1);
            if(col4 < data.getWidth() - 1) {
                rightMapElement = data.getMapElement(row4, col4);
            }

            //bind the data to the new map element
            vh.bind(data.getMapElement(row, col),
                    upMapElement, downMapElement, leftMapElement, rightMapElement,
                    row, col);
        }

        //private inner class for the map holders within the recycler view
        private class MapVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            // Image View for the foreground and background of the current grid cell
            private ImageView structureView;
            private ImageView backgroundView;
            // reference to the map element and the adjacent map elements
            private MapElement currentMapElement;
            private MapElement upMapElement;
            private MapElement downMapElement;
            private MapElement leftMapElement;
            private MapElement rightMapElement;
            // the row and column number of the current element within the grid
            private int row;
            private int column;
            // reference to the structure of the current map element
            private Structure structure;

            //purpose: constructor for creating a new viewHolder inflating the grid cell
            public MapVHolder(LayoutInflater li, ViewGroup parent) {
                super(li.inflate(R.layout.grid_cell, parent, false));

                // setting the size of the grid cell
                int size = parent.getMeasuredHeight() / gameData.getHeight() + 1;
                ViewGroup.LayoutParams lp = itemView.getLayoutParams();
                lp.width = size;
                lp.height = size;

                // retrieving the reference to the background and structure ImageView's
                backgroundView = (ImageView) itemView.findViewById(R.id.background);
                structureView = (ImageView) itemView.findViewById(R.id.structureView);

                //set an onclick listener for when the grid cell is selected
                itemView.setOnClickListener(this);

            }

            //purpose: method for binding information to a view holder
            public void bind(MapElement mapElement,
                             MapElement upMapElement, MapElement downMapElement,
                             MapElement leftMapElement, MapElement rightMapElement,
                             int row, int column) {
                //set view holder information
                currentMapElement = mapElement;
                structure = currentMapElement.getStructure();
                this.upMapElement = upMapElement;
                this.downMapElement = downMapElement;
                this.leftMapElement = leftMapElement;
                this.rightMapElement = rightMapElement;
                this.row = row;
                this.column = column;

                // by default set the structure view as transparent as there is currently
                // no structure on the grid cell
                structureView.setImageResource(android.R.color.transparent);

                // set the background ImageView to a grass drawable
                backgroundView.setImageResource(R.drawable.ic_grass1);

                // if the map element for this grid cell has a structure associated with it
                // then set the structure for the grid cell as the map elements structure
                if (mapElement.getStructure() != null) {
                    structureView.setImageResource(mapElement.getStructure().getImageId());
                }

                // if the current map element has an image associated with it then set the
                // resource to transparent to remove the drawable image and set the
                // bitmap image
                if (mapElement.getImage() != null)
                {
                    structureView.setImageResource(android.R.color.transparent);
                    structureView.setImageBitmap(mapElement.getImage());
                }
            }

            //purpose: method that is run when a user presses a grid cell
            @Override
            public void onClick(View view) {

                //reference to the structure selected by the user from the bottom recycler view
                Structure selectedStructure;

                // if the user has selected the details button on the MapScreen
                // and the current grid cell has a structure associated with it
                // begin details activity for that grid cell
                if(MapScreen.checkDetails() && structure != null)
                {
                    //set temporary variables for use when retrieving the results in the
                    //onActivityResult() method
                    tempStructure = structure;
                    tempMapElement = currentMapElement;
                    tempStructureView = structureView;
                    tempAdapterPosition = getAdapterPosition();

                    //begin the Details activity
                    startActivityForResult(DetailsScreen.retrieveIntent(getActivity(), row, column, structure),
                                                REQUEST_CODE_MAPVHOLDER);

                }
                // if the has selected the demo button on the MapScreen
                // and the current grid cell has a structure associated with it
                // remove the structure from the grid cell
                else if(MapScreen.checkDemo() && structure != null)
                {
                    //decrease the total number of that particular structure
                    MapScreen.decreaseStructureCount(structure);
                    //set the structure of the currentMap element to null to remove structure
                    currentMapElement.setStructure(null);
                    //set the structure to null to remove any thumbnail image being used by the structure
                    currentMapElement.setImage(null);
                    //set structure view back to transparent to remove structure
                    structureView.setImageResource(android.R.color.transparent);
                    //structureView.setImageBitmap(null);

                    //remove he map element from the database
                    gameData.removeMapElement(currentMapElement);

                    //currentMapElement.setImage(null);
                    /*most recent image view was also saved so we can set the resource to
                    transparent to delete the previous drawable and set the imageBitmap to
                    get the new photo in the grid cell*/
                    //tempStructureView.setImageResource(android.R.color.transparent);
                    //tempStructureView.setImageBitmap(newImage);
                }
                //if demo and the details buttons havn't been clicked the begin process
                //of adding new structure
                else if(MapScreen.checkDemo() == false && MapScreen.checkDetails() == false)
                {
                    //get currently selected structure from the bottom recycler view
                    selectedStructure = selectorFragment.getSelectedStructure();
                    // if there is currently a structure on the particular cell and a
                    // structure has been selected from the bottom recycler view continue process
                    if (structure == null && selectedStructure != null) {

                        // if user doesn't have enough money to create structure output message
                        if(gameData.getCurrentMoney() < selectedStructure.getCost())
                        {
                            Toast.makeText(getContext(), "Not Enough Money", Toast.LENGTH_SHORT).show();
                        }

                        //if the structure to create is a road or is a residential or commercial
                        //structure that is being placed next to a road
                        //and user has enough money then add structure to grid
                        if ((selectedStructure.isARoad()
                                || (upMapElement != null && upMapElement.isARoad())
                                || (downMapElement != null && downMapElement.isARoad())
                                || (leftMapElement != null && leftMapElement.isARoad())
                                || (rightMapElement != null && rightMapElement.isARoad()))
                                && gameData.getCurrentMoney() >= selectedStructure.getCost())
                        {
                            //set structure of current map element
                            currentMapElement.setStructure(selectedStructure.cloneStructure());
                            //set image on screen to structure's drawable
                            structureView.setImageResource(selectedStructure.getImageId());
                            //update money lost and the number of structures
                            MapScreen.setMoney(selectedStructure.getCost());
                            MapScreen.setNumStructures(selectedStructure);
                            //add new mapElement to 2D array of map elements
                            gameData.addMapElement(currentMapElement);
                        }
                    }
                }


                //update game data on screen
                mapAdapter.notifyItemChanged(getAdapterPosition());
            }
        }
    }

    //purpose: method for getting the return intent from the details screen.
    //         in particular it sets the new name and thumbnail photo for the
    //         grid coordinate if the user changed them
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnData)
    {
        //retrieve the return intent from the details screen
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_MAPVHOLDER)
        {
            //retrieve the any new structure name or image created within the
            //details activity
            String newStructureName = DetailsScreen.getStructureName(returnData);
            Bitmap newImage = DetailsScreen.getImage(returnData);
            //if the new name isn't null then a new name was created. Set the new name
            if(newStructureName != null)
            {
                //the most recent structure to be edited was saved as a private variable. Hence
                //we can access it and set the new name
                tempStructure.setName(newStructureName);
                //update database of a name change
                gameData.updateMapElements(tempMapElement);
            }

            //if the new image is not null then a new image was set for this grid cell. Set the
            //new grid cell thumbnail
            if(newImage != null)
            {
                //most recent map element was saved as a private field. Hence we can access it
                //and set the new thumbnail image
                tempMapElement.setImage(newImage);
                /*most recent image view was also saved so we can set the resource to
                  transparent to delete the previous drawable and set the imageBitmap to
                  get the new photo in the grid cell*/
                tempStructureView.setImageResource(android.R.color.transparent);
                tempStructureView.setImageBitmap(newImage);
            }

            //set the private fields back to null for use the next time a
            //grid cell is selected for editing
            tempStructure = null;
            tempMapElement = null;
            tempStructureView = null;

            //notify an item changed to update the grid cells that have changed
            //tempAdapterPosition being the position of the most recent grid cell that was edited
            mapAdapter.notifyItemChanged(tempAdapterPosition);

        }
    }
}
