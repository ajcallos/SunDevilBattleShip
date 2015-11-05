package com.aespurge.sundevilbattleship;


import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import com.aespurge.sundevilbattleship.Game.Facing;
import com.aespurge.sundevilbattleship.Game.Vector2d;
import com.aespurge.sundevilbattleship.Game.ships.AircraftCarrier;
import com.aespurge.sundevilbattleship.Game.ships.Battleship;
import com.aespurge.sundevilbattleship.Game.ships.Cruiser;
import com.aespurge.sundevilbattleship.Game.ships.Destroyer;
import com.aespurge.sundevilbattleship.Game.ships.Submarine;
import com.aespurge.sundevilbattleship.Game.ships.Warship;

public class GameplayActivity extends AppCompatActivity {

    // GridLayout for the playing board
    GridLayout gridLayout;
    //2d array for holding all the image tiles
    Tile[][] tiles;
    //Points to the currently selected tile
    Tile selectedTile;
    Button fireButton;
    Warship[] shipList;
    Warship grabbedShip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        fireButton = (Button)findViewById(R.id.fireButton);
        shipList = new Warship[5];
        tiles = new Tile[10][10];
        populateShipList();
        //Populate the grid with basic bordered sea tiles
        populateImageGrid();
        //Have to initialize the selectedTile.
        selectedTile = tiles[0][0];
        populateShips();

    }

    public void drawShip(Warship ship) {
        int x = ship.getLocation().getX();
        int y = ship.getLocation().getY();
        for(int i = 0; i < ship.getLength(); i++) {
            if (ship.getFacing() == Facing.North) {
                tiles[x][y + i].setImageResource(R.drawable.ship);
                tiles[x][y + i].setOnTouchListener(new MyTouchListener());
                tiles[x][y + i].setIsSea(false);
                tiles[x][y + i].setIsShip(true);
                tiles[x][y + i].setShip(ship);
            } else {
                tiles[x][y + i].setImageResource(R.drawable.ship);
                tiles[x][y + i].setOnTouchListener(new MyTouchListener());
                tiles[x][y + i].setIsSea(false);
                tiles[x][y + i].setIsShip(true);
                tiles[x][y + i].setShip(ship);
            }
        }
    }

    private void undrawShip(Warship ship) {
        int x = ship.getLocation().getX();
        int y = ship.getLocation().getY();
        for(int i = 0; i < ship.getLength(); i++){
            if (ship.getFacing() == Facing.North){
                tiles[x][y + i].setImageResource(R.drawable.sea);
                tiles[x][y + i].setOnTouchListener(null);
                tiles[x][y + i].setIsSea(true);
                tiles[x][y + i].setIsShip(false);
                tiles[x][y + i].setShip(null);
            }else{
                tiles[x + i][y].setImageResource(R.drawable.sea);
                tiles[x + i][y].setOnTouchListener(null);
                tiles[x + i][y].setIsSea(true);
                tiles[x + i][y].setIsShip(false);
                tiles[x + i][y].setShip(null);
            }
        }
    }

    private boolean checkViableShipLocation(Warship ship) {
        int length = ship.getLength();
        int x = ship.getLocation().getX();
        int y = ship.getLocation().getY();
        if(ship.getFacing() == Facing.North){
            if(y+length > 10)
                return false;
        }else{
            if(x+length > 10)
                return false;
        }
        for(int i = 0; i < ship.getLength(); i++) {
            if (ship.getFacing() == Facing.North) {
                if (tiles[x][y + i].isShip()) {
                    return false;
                }
            } else {
                if (ship.getFacing() == Facing.North) {
                    if (tiles[x + i][y].isShip()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void populateShips() {
        for (Warship ship : shipList)
        {
            drawShip(ship);
        }
    }

    private void populateShipList() {
        shipList[0] = new Battleship(new Vector2d(0,0), Facing.North);
        shipList[1] = new AircraftCarrier(new Vector2d(2,0), Facing.North);
        shipList[2] = new Cruiser(new Vector2d(4,0), Facing.North);
        shipList[3] = new Submarine(new Vector2d(6,0), Facing.North);
        shipList[4] = new Destroyer(new Vector2d(8,0), Facing.North);
    }


    //Populates grid with water tiles, and gives them all listeners.
    private void populateImageGrid() {
        for(int y=0; y<10; y++) {
            for (int x = 0; x < 10; x++) {
                tiles[x][y] = new Tile(this, x, y);
                tiles[x][y].setImageResource(R.drawable.sea);
                gridLayout.addView(tiles[x][y]);
                tiles[x][y].setClickable(true);
                tiles[x][y].setLongClickable(true);

                //Right now the clicks just highlight sea tiles. I'll need to make this better to account for different tiles.d
                tiles[x][y].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View tile) {
                        selectTile((Tile) tile);
                        return true;
                    }
                });
                tiles[x][y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View tile) {
                        selectTile((Tile) tile);
                    }
                });
                tiles[x][y].setOnDragListener(new MyDragListener());
            }
        }
    }

    //Sets selected tile to "selected" and changes image to highlighted version.
    void selectTile(Tile tile){
        if(selectedTile.isSea()) {
            selectedTile.setImageResource(R.drawable.sea);
            tile.setIsSelected(false);
        }
        if(selectedTile.isSea() && selectedTile.isShot()) {
            selectedTile.setImageResource(R.drawable.sea_explosion);
            tile.setIsSelected(true);
        }
        if(selectedTile.isShip()) {
            selectedTile.setImageResource(R.drawable.ship);
            tile.setIsSelected(true);
        }
        if(selectedTile.isShip() && selectedTile.isShot()) {
            selectedTile.setImageResource(R.drawable.ship_explosion);
            tile.setIsSelected(true);
        }
        selectedTile = (Tile) tile;
        if (tile.isSea()) {
            tile.setImageResource(R.drawable.sea_selected);
            tile.setIsSelected(true);
        }
        if (tile.isSea() && tile.isShot()) {
            tile.setImageResource(R.drawable.sea_explosion_selected);
            tile.setIsSelected(true);
        }
        if (tile.isShip()) {
            tile.setImageResource(R.drawable.ship_selected);
            tile.setIsSelected(true);
        }
        if (tile.isShip() && tile.isShot()) {
            tile.setImageResource(R.drawable.ship_explosion_selected);
            tile.setIsSelected(true);
        }

    }

    //Checks selected tile, then changes the image to the appropriate explosion type.
    public void onFire(View v){
        if (selectedTile.isSea()) {
            selectedTile.setImageResource(R.drawable.sea_explosion);
            selectedTile.setIsShot(true);
        }
        if (selectedTile.isShip()) {
            selectedTile.setImageResource(R.drawable.ship_explosion);
            selectedTile.setIsShot(true);
        }
    }

    private class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            Tile tile = (Tile) view;
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                grabbedShip = tile.getShip();
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }else{
                return false;
            }

        }
    }

    public class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            Tile tile = (Tile) v;
            Vector2d previousLocation = grabbedShip.getLocation();

            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    undrawShip(grabbedShip);
                    grabbedShip.setLocation(tile.getXCoordinate(), tile.getYCoordinate());
                    if(!checkViableShipLocation(grabbedShip))
                        grabbedShip.setLocation(previousLocation.getX(), previousLocation.getY());
                    drawShip(grabbedShip);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                default:
                    break;
            }
            return true;
        }
    }
}


