package com.example.mad_assignment.Activities_Fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mad_assignment.R;
import com.example.mad_assignment.Data.Structure;

/*Author: Alex McLeod
  Purpose: An Activity class to show the user the details of a particular grid cell. With
           specific details they are able to edit.
  Date Modified: 14/10/2018
 */

public class DetailsScreen extends AppCompatActivity
{
    //edited information returned to the Map fragment
    private Intent returnData;

    //key values for
    private static final String ROW = "com.example.row";
    private static final String COLUMN = "com.example.column";
    private static final String STRUCTURE = "com.example.structure";
    private static final String GET_STRUCTURE = "com.example.getStructure";
    private static final String GET_THUMBNAIL = "com.example.getThumbnail";
    private static final int REQUEST_THUMBNAIL = 1;

    private TextView gridCoordinateView;
    private TextView structureTypeView;
    private TextView structureNameView;

    private Button saveNameBtn;
    private Button thumbnailBtn;
    private Button backBtn;

    private ImageView thumbnailImageView;

    private int row;
    private int column;
    private Structure structure;

    //purpose: method for creating the intent for this activity.
    public static Intent retrieveIntent(Context c, int newRow, int newColumn, Structure newStructure)
    {
        Intent intent = new Intent(c, DetailsScreen.class);
        //storing the information needed for this activity in the intent
        intent.putExtra(ROW, newRow);
        intent.putExtra(COLUMN, newColumn);
        intent.putExtra(STRUCTURE, newStructure);
        return intent;
    }

    //purpose: method that starts upon the creation of this activity. Retrieves and sets
    //         Buttons and TextView as well as creating on click listeners
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);

        //retrieve the intent for this activity
        Intent intent = getIntent();
        row = intent.getIntExtra(ROW, 0);
        column = intent.getIntExtra(COLUMN, 0);
        structure = (Structure) intent.getSerializableExtra(STRUCTURE);

        //create the return intent for this activity
        returnData = new Intent();

        //retrieve text view from Details xml layout
        gridCoordinateView = (TextView) findViewById(R.id.gridCoordinatesView);
        structureTypeView = (TextView) findViewById(R.id.structureTypeView);
        structureNameView = (TextView) findViewById(R.id.structureNameView);

        //retrieve Buttons from Details xml layout
        saveNameBtn = (Button) findViewById(R.id.saveNameBtn);
        thumbnailBtn = (Button) findViewById(R.id.thumbnailBtn);
        backBtn = (Button) findViewById(R.id.backBtn);

        //retrieve ImageView for holding the thumbnail photo
        thumbnailImageView = (ImageView) findViewById(R.id.thumbnailImageView);

        //set the values for TextViews. Including grid coordinates, structure type and structure name
        gridCoordinateView.setText("row = " + Integer.toString(row)
                + ", column = " + Integer.toString(column));
        structureTypeView.setText("Structure Type: " + structure.getType());
        structureNameView.setText(structure.getName());

        //creates an onclick listener for the save button.
        //if the user selects the saveNameBtn it will save the name from the TextView as the
        //structures new name.
        saveNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set tempName singleton from the structure class
                Structure.setTempName(structureNameView.getText().toString());
                //set return intent to return the new name of the structure
                returnData.putExtra(GET_STRUCTURE, structureNameView.getText().toString());
            }
        });

        //creates onclick listener for the thumbnail btn
        //once clicked the user is able to take a photo to be set as the structures new thumbnail
        //photo
        thumbnailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // access the systems default camera and allows the user to take a photo
                // creates a new return intent that contains the new photo.
                Intent thumbnailIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //send return intent to previous fragment to set new thumbnail
                startActivityForResult(thumbnailIntent, REQUEST_THUMBNAIL);

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK, returnData);
                finish();
            }
        });


    }

    //purpose: method for retrieve the new structure name from the return intent
    public static String getStructureName(Intent intent)
    {
        return intent.getStringExtra(GET_STRUCTURE);
    }

    //purpose: method for retrieving the new thumbnail photo from the return intent
    public static Bitmap getImage(Intent intent)
    {
        return intent.getParcelableExtra(GET_THUMBNAIL);
    }

    //purpose: method for retrieving the return intent from the camera so we can retrieve
    //         the new thumbnail photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        super.onActivityResult(requestCode, resultCode, resultIntent);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_THUMBNAIL) {
            //retrieve the thumbnail image from the return intent
            Bitmap thumbnail = (Bitmap) resultIntent.getExtras().get("data");
            //set the Detail Screens imageView to the new thumbnail
            thumbnailImageView.setImageBitmap(thumbnail);
            //store as the return intent for the details screen so we can send it back to
            //the Map grid
            returnData.putExtra(GET_THUMBNAIL, thumbnail);
        }
    }
}
