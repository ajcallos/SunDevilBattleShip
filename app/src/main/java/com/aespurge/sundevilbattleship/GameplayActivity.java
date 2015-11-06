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

import java.util.Random;

public class GameplayActivity extends AppCompatActivity {

    GridLayout gridLayout; //GridLayout for the playing board
    Tile[][] myTiles, enemyTiles, board; //2d array for holding all the image tiles
    Tile selectedTile; //Points to the currently selected tile
    Button button; //Button at the bottom of the screen, for advancing the game.
    Warship[] myShips, enemyShips; //Array to hold all the ships.
    Warship grabbedShip; //This helps with drag'n'drop.
    Random rand = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        button = (Button)findViewById(R.id.button);
        myTiles = new Tile[10][10];
        board = myTiles;
        enemyTiles = new Tile[10][10];
        populateImageGrid(myTiles, false);
        populateImageGrid(enemyTiles, true);
        selectedTile = null;//Have to initialize the selectedTile.
        myShips = new Warship[5];
        enemyShips = new Warship[5];
        populateShipList(myShips);
        populateShipList(enemyShips);
        populateShips(myShips, myTiles);
        populateShips(enemyShips, enemyTiles);
        addBoard();
    }

    private void addBoard() {
        for(Tile[] x : myTiles)
            for(Tile y : x)
                gridLayout.addView(y);
        for(Tile[] x : enemyTiles)
            for(Tile y : x){
                gridLayout.addView(y);
                y.setVisibility(View.GONE);
            }

    }

    private void switchBoards(){
        for(Tile[] x : myTiles) {
            for (Tile y : x) {
                if (y.getVisibility() == View.GONE) {
                    y.setVisibility(View.VISIBLE);
                } else {
                    y.setVisibility(View.GONE);
                }
            }
        }
        for(Tile[] x : enemyTiles) {
            for (Tile y : x) {
                if (y.getVisibility() == View.GONE) {
                    y.setVisibility(View.VISIBLE);
                } else {
                    y.setVisibility(View.GONE);
                }
            }
        }
    }

    //Draws the ships. Changes all the necessary tile properties and the image to a ship.
    public void drawShip(Warship ship, Tile[][] tiles) {
        int x = ship.getLocation().getX();
        int y = ship.getLocation().getY();
        for(int i = 0; i < ship.getLength(); i++) {
            if (ship.getFacing() == Facing.North) {
                tiles[x][y + i].setOnTouchListener(new MyTouchListener());
                tiles[x][y + i].setOnClickListener(new shipClickListener());
                tiles[x][y + i].setIsSea(false);
                tiles[x][y + i].setIsShip(true);
                tiles[x][y + i].setShip(ship);
                drawMap(tiles);
            } else {
                tiles[x + i][y].setOnTouchListener(new MyTouchListener());
                tiles[x + i][y].setOnClickListener(new shipClickListener());
                tiles[x + i][y].setIsSea(false);
                tiles[x + i][y].setIsShip(true);
                tiles[x + i][y].setShip(ship);
                drawMap(tiles);
            }
        }
    }

    //Undraws the ship. So, sets it back to a plain ol' sea tile.
    private void undrawShip(Warship ship, Tile[][] tiles) {
        int x = ship.getLocation().getX();
        int y = ship.getLocation().getY();
        for(int i = 0; i < ship.getLength(); i++){
            if (ship.getFacing() == Facing.North) {
                tiles[x][y + i].setOnTouchListener(null);
                tiles[x][y + i].setOnClickListener(null);
                tiles[x][y + i].setIsSea(true);
                tiles[x][y + i].setIsShip(false);
                tiles[x][y + i].setShip(null);
                drawMap(tiles);
            }else{
                tiles[x + i][y].setOnTouchListener(null);
                tiles[x + i][y].setOnClickListener(null);
                tiles[x + i][y].setIsSea(true);
                tiles[x + i][y].setIsShip(false);
                tiles[x + i][y].setShip(null);
                drawMap(tiles);
            }
        }
    }

    //First checks to make sure the ship won't be out of bounds of the grid. Then it checks to make sure it won't
    //overlap anything.
    private boolean checkViableShipLocation(Warship ship, Tile[][] tiles) {
        Facing facing = ship.getFacing();
        int length = ship.getLength();
        int x = ship.getLocation().getX();
        int y = ship.getLocation().getY();
        if(facing == Facing.North){
            if(y+length > 10)
                return false;
        }else{
            if(x+length > 10)
                return false;
        }
        for(int i = 0; i < length; i++) {
            if (ship.getFacing() == Facing.North) {
                if (tiles[x][y + i].isShip()) {
                    return false;
                }
            } else {
                if (tiles[x + i][y].isShip()) {
                    return false;
                }
            }
        }
        return true;
    }

    //Just draws all the ships.
    private void populateShips(Warship[] ships, Tile[][] tiles) {
        for (Warship ship : ships)
        {
            do{
                if(rand.nextBoolean())
                    ship.switchFacing();
                ship.setLocation(rand.nextInt(9), rand.nextInt(9));
            }while(!checkViableShipLocation(ship, tiles));

            drawShip(ship, tiles);
        }
    }

    //Fills the myShips array with ships. Starting positions are always the same.
    private void populateShipList(Warship[] ships) {
        ships[0] = new AircraftCarrier(new Vector2d(0,0), Facing.North);
        ships[1] = new Battleship(new Vector2d(2,0), Facing.North);
        ships[2] = new Cruiser(new Vector2d(4,0), Facing.North);
        ships[3] = new Submarine(new Vector2d(6,0), Facing.North);
        ships[4] = new Destroyer(new Vector2d(8,0), Facing.North);
    }


    private void drawMap(Tile[][] tiles) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                int drawable;
                if (tiles[x][y].isShip()) {
                    if (tiles[x][y].getShip().getFacing() == Facing.North) {
                        drawable = y - tiles[x][y].getShip().getLocation().getY();
                        tiles[x][y].setImageResource(tiles[x][y].getShip().getDrawables()[drawable]);
                    } else {
                        drawable = x - tiles[x][y].getShip().getLocation().getX();
                        tiles[x][y].setImageResource(tiles[x][y].getShip().getDrawables()[drawable]);
                    }
                } else {
                    if (tiles[x][y].isShot()) {
                        tiles[x][y].setImageResource(R.drawable.sea_explosion);
                    } else {
                        if (tiles[x][y].isSelected()) {
                            tiles[x][y].setImageResource(R.drawable.sea_selected);
                        } else {
                            tiles[x][y].setImageResource(R.drawable.sea);
                        }
                    }
                }
            }
        }
    }

    //Populates grid with water tiles, and gives them all listeners.
    private void populateImageGrid(Tile[][] tiles, boolean isEnemy) {
        for(int y=0; y<10; y++) {
            for (int x = 0; x < 10; x++) {
                tiles[x][y] = new Tile(this, x, y, isEnemy);
                tiles[x][y].setClickable(true);

                //Right now the clicks just highlight sea tiles. I'll need to make this better to account for different tiles.
                if(isEnemy){
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
                }else{
                    tiles[x][y].setOnDragListener(new MyDragListener());
                }
            }
        }
        drawMap(tiles);
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
        selectedTile = tile;
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
        int shotLocation;
        switchBoards();
        if(selectedTile != null) {
            if (selectedTile.isSea()) {
                selectedTile.setImageResource(R.drawable.sea_explosion);
                selectedTile.setIsShot(true);
            }
            if (selectedTile.isShip()) {
                if (selectedTile.getShip().getFacing() == Facing.North) {
                    shotLocation = selectedTile.getShip().getLocation().getY() - selectedTile.getYCoordinate();
                } else {
                    shotLocation = selectedTile.getShip().getLocation().getX() - selectedTile.getXCoordinate();
                }
                selectedTile.setImageResource(R.drawable.ship_explosion);
                selectedTile.setIsShot(true);
                if (selectedTile.getShip().damage(shotLocation))
                    selectedTile.getShip().sink();
            }
        }
    }

    //Turns the ships. Undraws it, turns it, checks to see if it's a valid position, and either draws it, or turns it back then draws it.
    private void turnShip(Warship ship, Tile[][] tiles){
        undrawShip(ship, tiles);
        ship.switchFacing();
        if(checkViableShipLocation(ship, tiles)){
            drawShip(ship, tiles);
        }else{
            ship.switchFacing();
            drawShip(ship, tiles);
        }
    }

    //Starts the grab for drag'n'drop.
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

    //Drag'n'drop for ships. I left all the cases 'cuz that's how the tutorial I was watching did it and I don't wanna mess with it yet.
    public class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            Tile tile = (Tile) v;
            Vector2d previousLocation = grabbedShip.getLocation();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:
                    undrawShip(grabbedShip, myTiles);
                    grabbedShip.setLocation(tile.getXCoordinate(), tile.getYCoordinate());
                    if(!checkViableShipLocation(grabbedShip, myTiles))
                        grabbedShip.setLocation(previousLocation.getX(), previousLocation.getY());
                    drawShip(grabbedShip, myTiles);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                default:
                    break;
            }
            return true;
        }
    }

    //Listener to turn the ships when you tap on 'em.
    private class shipClickListener implements View.OnClickListener {
        @Override
        public void onClick(View tile) {
            turnShip(((Tile) tile).getShip(), myTiles);
        }
    }
}


