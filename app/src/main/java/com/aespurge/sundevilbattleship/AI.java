package com.aespurge.sundevilbattleship;

import com.aespurge.sundevilbattleship.Game.Facing;
import com.aespurge.sundevilbattleship.Game.ships.Warship;

import java.util.Random;

/**
 * Created by Anthony on 11/21/2015.
 */
public class AI {
    Warship[] ships;
    Tile[][] grid;

    AI(Warship[] ships){
        this.ships = ships;
        this.grid = GameplayActivity.getTiles();
    }

    public Tile getShot(){
        int x = 0;
        int y = 0;
        for(Warship ship : ships) {
            if (!ship.isSunk()) { //Check if ship is already sunk.
                if (ship.isDamaged()) { //Check to see if you've already hit it at least once.
                    int damageCount = getDamageCount(ship);
                    if (damageCount == 1) { //If it's only been hit once..
                        if (ship.getFacing() == Facing.North) { //This is just to get the X/Y coordinates of the one hit.
                            x = ship.getLocation().getX();
                            y = firstHit(ship);
                        } else {
                            x = firstHit(ship);
                            y = ship.getLocation().getY();
                        }
                        if (y < 9) //So you don't overflow the grid.
                            if (!grid[x][y + 1].isShot()) //Check the tile below. If it's not shot yet, shoot it.
                                return grid[x][y + 1];
                        if (x < 9) //So you don't overflow the grid.
                            if (!grid[x + 1][y].isShot()) //Check the tile to the right. If it's not shot yet, shoot it.
                                return grid[x + 1][y];
                        if (y > 0) //So you don't overflow the grid.
                            if (!grid[x][y - 1].isShot()) //Check the tile above. If it's not shot yet, shoot it.
                                return grid[x][y - 1];
                        if (x > 0) //So you don't overflow the grid.
                            if (!grid[x - 1][y].isShot()) //Check the tile to the left. If it's not shot yet, shoot it.
                                return grid[x - 1][y];
                    } else { //If there's more than one damage on the ship.
                        if (ship.getFacing() == Facing.North) { //If it's up-and-down
                            x = ship.getLocation().getX();
                            y = firstHit(ship);
                            if (isSequential(ship.getDamage())) { //As long as all the hits are together,
                                if (y > 0) { //So you don't overflow the grid.
                                    if (!grid[x][y - 1].isShot()) { //Check the tile above. If it's not shot yet, shoot it.
                                        return grid[x][y - 1];
                                    }
                                    y = lastHit(ship); //Shoot the bottom tile.
                                    if (y < 9) { //So you don't overflow the grid.
                                       if(!grid[x][y+1].isShot()) //Check the tile below. If it's not shot yet, shoot it.
                                           return grid[x][y + 1];
                                    }
                                }
                            } else { //If the shots aren't all together
                                for (int i = firstHit(ship) + 1; i < lastHit(ship); i++)
                                    if (!grid[x][i].isShot()) { //Shoot the first unshot square between them.
                                        return grid[x][i];
                                    }
                            }
                        } else { //If the ship is left-to-right
                            y = ship.getLocation().getY();
                            x = firstHit(ship);
                            if (isSequential(ship.getDamage())) { //As long as all the hits are together,
                                if (x > 0) //So you don't overflow the grid.
                                    if (!grid[x - 1][y].isShot()) {
                                        return grid[x - 1][y];
                                    }
                                    x = lastHit(ship);
                                    if (x < 9) { //So you don't overflow the grid.
                                        if(!grid[x+1][y].isShot()) //Check the tile below. If it's not shot yet, shoot it.
                                            return grid[x + 1][y];
                                    }
                            } else {
                                for (int i = firstHit(ship) + 1; i < lastHit(ship); i++){
                                    if (!grid[i][y].isShot()) {
                                        return grid[i][y];
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } //If all else fails, pick a random square!
        Random rand = new Random();
        do{
            x = rand.nextInt(10);
            y = rand.nextInt(10);
        }while(grid[x][y].isShot());
        return grid[x][y];
    }

    private int getDamageCount(Warship ship) {
        int damageCount = 0;
        for(boolean isDamaged : ship.getDamage())
            if(isDamaged)
                damageCount++;
        return damageCount;
    }

    private int lastHit(Warship ship) {
        if(ship.getFacing() == Facing.North) {
            for(int i = ship.getLength()-1; i >= 0; i--) {
                if(ship.getDamage()[i]){
                    return ship.getLocation().getY()+i+1;
                }
            }
        }else{
            for(int i = 0; i < ship.getLength(); i++) {
                if(ship.getDamage()[i]){
                    return ship.getLocation().getX()+i+1;
                }
            }
        }
        return -1;
    }

    private int firstHit(Warship ship) {
        if(ship.getFacing() == Facing.North) {
            for(int i = 0; i < ship.getLength(); i++) {
                if(ship.getDamage()[i]){
                    return ship.getLocation().getY()+i;
                }
            }
        }else{
            for(int i = 0; i < ship.getLength(); i++) {
                if(ship.getDamage()[i]){
                    return ship.getLocation().getX()+i;
                }
            }
        }
        return -1;
    }

    private boolean isSequential(boolean[] damage) {
        boolean damageFlag = false;
        boolean tripped = false;
        for(int i = 0; i < damage.length; i ++){
            if(damage[i]){
                damageFlag = true;
                if(tripped){
                    return false;
                }
            }else{
                if(damageFlag)
                    tripped = true;
            }
        }
        return true;
    }
}
