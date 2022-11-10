package com.example.mad_assignment.Activities_Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mad_assignment.R;
import com.example.mad_assignment.Data.Structure;
import com.example.mad_assignment.Data.StructureData;

/*Author: Alex McLeod
  Purpose: Fragment for holding the recycler view using a linear layout, that displays the
           different structures that you can select and then place on the map grid. Once
           a structure from this menu has been selected a reference is stored.
  Date Modified: 15/10/2019
 */

public class Selector extends Fragment {

    // reference to selected structure from the list
    private static Structure selectedStructure = null;
    // reference to the recycler views adapter
    private SelectorAdapter selectorAdapter;

    public Selector() {
        // Required empty public constructor
    }

    //purpose: method that initiates recycler view linear layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // for storing the structure data containing drawable references
        StructureData structureData;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selector, container, false);

        //retrieve recycler view from fragment
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.selectorRecyclerView);

        //set information for new recycler view
        //set as a linear layout
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //set reference to structureData information
        structureData = StructureData.get();

        //create Adapter
        selectorAdapter = new SelectorAdapter(structureData);
        rv.setAdapter(selectorAdapter);

        return view;
    }

    //purpose: method for retrieving the currently selected structure
    public static Structure getSelectedStructure() {
        return selectedStructure;
    }

    //purpose: private inner adapter class for selector fragment
    private class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.StructureVHolder> {

        //for storing the reference to the strucuture data
        private StructureData data;

        //constructor for setting reference to structure data
        public SelectorAdapter(StructureData data) {
            this.data = data;
        }

        //purpose: method for returning the number of elements in the list
        @Override
        public int getItemCount() {
            return data.size();
        }

        //method for creating new ViewHolder when needed by the recycler
        @Override
        public StructureVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new StructureVHolder(li, parent);
        }

        //purpose: method called when recycler view needs to update a viewHolder to display new data
        @Override
        public void onBindViewHolder(StructureVHolder vh, int index) {
            vh.bind(data.get(index));
        }

        //private inner class for the structure holders within the recycler view
        private class StructureVHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            //reference to elements of the view holder
            private ImageView elementImageView;
            private TextView elementTextView;
            private Structure currentStructure;

            //constructor for creating a new viewholder inflating the elements xml
            public StructureVHolder(LayoutInflater li, ViewGroup parent) {
                super(li.inflate(R.layout.list_selection, parent, false));
                //obtain the reference to the image view for the drawable picture and
                //the text view for the name of structure
                elementImageView = (ImageView) itemView.findViewById(R.id.elementImageView);
                elementTextView = (TextView) itemView.findViewById(R.id.elementTextView);

                //set onclick listener for when the user selects a structure
                itemView.setOnClickListener(this);
            }

            //purpose: binds a structure to the view holder
            public void bind(Structure structure) {

                currentStructure = structure;
                elementImageView.setImageResource(structure.getImageId());
                elementTextView.setText(structure.getLabel());
            }

            //purpose: method for when a structure is selected from the list
            @Override
            public void onClick(View view)
            {
                //upon clicking both demolish and details mode are switched off
                MapScreen.turnDemoOff();
                MapScreen.turnDetailsOff();
                //reference to recently selected structure us set
                selectedStructure = currentStructure;
            }
        }
    }
}