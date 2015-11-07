package com.aespurge.sundevilbattleship;

import android.content.Context;
import android.widget.ImageView;

import com.aespurge.sundevilbattleship.Game.Facing;
import com.aespurge.sundevilbattleship.Game.ships.Warship;

/**
 * Created by Anthony on 11/4/2015.
 */
public class Tile extends ImageView {

    private Warship ship;
    private int x;
    private int y;
    private boolean isSelected;
    private boolean isSea;
    private boolean isShip;
    private boolean isShot;



    private boolean isEnemy;

    public Tile(Context context, int x, int y, boolean isEnemy) {
        super(context);
        this.x = x;
        this.y = y;
        this.isEnemy = isEnemy;
        this.isSelected = false;
        this.isSea = true;
        this.isShip = false;
        this.isShot = false;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public boolean isShot() {
        return isShot;
    }

    public void setIsShot(boolean beenShot) {
        this.isShot = beenShot;
    }

    public boolean isSea() {
        return isSea;
    }

    public void setIsSea(boolean isSea) {
        this.isSea = isSea;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isShip() {
        return isShip;
    }

    public void setIsShip(boolean isShip) {
        this.isShip = isShip;
    }

    public Warship getShip() {
        return ship;
    }

    public void setShip(Warship ship) {
        this.ship = ship;
    }

    public int getXCoordinate() {
        return x;
    }

    public int getYCoordinate() {
        return y;
    }
}
