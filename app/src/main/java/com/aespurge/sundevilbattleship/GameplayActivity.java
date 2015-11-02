package com.aespurge.sundevilbattleship;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;

public class GameplayActivity extends AppCompatActivity {

    // GridLayout for the playing board
    GridLayout gridLayout;
    //2d array for holding all the image tiles
    ImageView[][] imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        gridLayout = (GridLayout)findViewById(R.id.gridLayout);

        //2d array for holding all the image tiles
        imageList = new ImageView[11][11];

        //Populate the grid with basic bordered sea tiles
        for(int y=1; y<11; y++)
            for(int x=1; x<11; x++) {
                imageList[x][y] = new ImageView(this);
                imageList[x][y].setImageResource(R.drawable.sea);
                gridLayout.addView(imageList[x][y]);
            }
    }
}
