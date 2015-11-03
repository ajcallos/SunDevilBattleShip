package com.aespurge.sundevilbattleship;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

public class GameplayActivity extends AppCompatActivity {

    // GridLayout for the playing board
    GridLayout gridLayout;
    //2d array for holding all the image tiles
    ImageView[][] imageList;
    //Points to the currently selected tile
    ImageView selectedTile;
    int selectedX = 0, selectedY = 0;
    Button fireButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        fireButton = (Button)findViewById(R.id.fireButton);
        imageList = new ImageView[10][10];
        //Populate the grid with basic bordered sea tiles
        populateImageGrid();
        //Have to initialize the selectedTile.
        selectedTile = imageList[0][0];


    }


    //Populates grid with water tiles, and gives them all listeners.
    private void populateImageGrid() {
        for(int y=0; y<10; y++) {
            for (int x = 0; x < 10; x++) {
                imageList[x][y] = new ImageView(this);
                imageList[x][y].setImageResource(R.drawable.sea);
                imageList[x][y].setTag("sea");
                gridLayout.addView(imageList[x][y]);
                imageList[x][y].setClickable(true);
                imageList[x][y].setLongClickable(true);

                //Each ImageView item's id will be 0-99, according to its x/y coordinates
                imageList[x][y].setId(10 * x + y);


                //Right now the clicks just highlight sea tiles. I'll need to make this better to account for different tiles.d
                imageList[x][y].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        selectTile(v);
                        return true;
                    }
                });
                imageList[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectTile(v);
                    }
                });
            }
        }
    }

    //Sets selected tile to "selected" and changes image to highlighted version.
    void selectTile(View v){
        if(selectedTile.getTag() == "sea_selected") {
            selectedTile.setImageResource(R.drawable.sea);
            selectedTile.setTag("sea");
        }
        if(selectedTile.getTag() == "sea_explosion_selected") {
            selectedTile.setImageResource(R.drawable.sea_explosion);
            selectedTile.setTag("sea_explosion");
        }
        selectedTile = (ImageView) v;
        selectedX = v.getId() / 10;
        selectedY = v.getId() % 10;
        if (v.getTag()=="sea") {
            imageList[selectedX][selectedY].setImageResource(R.drawable.sea_selected);
            v.setTag("sea_selected");
        }
        if (v.getTag()=="sea_explosion") {
            imageList[selectedX][selectedY].setImageResource(R.drawable.sea_explosion_selected);
            v.setTag("sea_explosion_selected");
        }

    }

    //Checks selected tile, then changes the image to the appropriate explosion type.
    public void onFire(View v){
        if (selectedTile.getTag() == "sea_selected") {
            imageList[selectedX][selectedY].setImageResource(R.drawable.sea_explosion);
            selectedTile.setTag("sea_explosion");
        }
        if (selectedTile.getTag() == "ship_selected") {
            imageList[selectedX][selectedY].setImageResource(R.drawable.ship_explosion);
            selectedTile.setTag("ship_explosion");
        }
    }
}


