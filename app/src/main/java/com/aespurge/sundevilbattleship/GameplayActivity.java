package com.aespurge.sundevilbattleship;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

public class GameplayActivity extends AppCompatActivity {

    // GridLayout for the playing board
    GridLayout gridLayout;
    //2d array for holding all the image tiles
    ImageView[][] imageList;
    ImageView selectedTile;
    int selectedX = 0, selectedY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        //2d array for holding all the image tiles
        imageList = new ImageView[10][10];

        //Populate the grid with basic bordered sea tiles
        for(int y=0; y<10; y++) {
            for (int x = 0; x < 10; x++) {
                imageList[x][y] = new ImageView(this);
                imageList[x][y].setImageResource(R.drawable.sea);
                gridLayout.addView(imageList[x][y]);
                imageList[x][y].setClickable(true);
                imageList[x][y].setLongClickable(true);
                imageList[x][y].setId(10*x+y);


                imageList[x][y].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        selectedTile = (ImageView) v;
                        imageList[selectedX][selectedY].setImageResource(R.drawable.sea);
                        selectedX = v.getId()/10;
                        selectedY = v.getId()%10;
                        imageList[selectedX][selectedY].setImageResource(R.drawable.sea_selected);
                        return true;
                    }
                });
                imageList[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedTile = (ImageView) v;
                        imageList[selectedX][selectedY].setImageResource(R.drawable.sea);
                        selectedX = v.getId()/10;
                        selectedY = v.getId()%10;
                        imageList[selectedX][selectedY].setImageResource(R.drawable.sea_selected);
                    }
                });

            }
        }
    }
}
