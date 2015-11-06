/**
 * Created by Alexander on 10/29/2015.
 */

package com.aespurge.sundevilbattleship.Game.ships;


import com.aespurge.sundevilbattleship.Game.Facing;
import com.aespurge.sundevilbattleship.Game.Vector2d;
import com.aespurge.sundevilbattleship.Game.WarshipType;
import com.aespurge.sundevilbattleship.R;

public class AircraftCarrier implements Warship
{
    private final int shipLength = 5;
    private final int firepower = 5;
    private final WarshipType type = WarshipType.AircraftCarrier;
    private Facing facing;
    private Vector2d location;
    private final int[] drawables;
    private boolean sunk = false;

    private int[] damage = new int[shipLength];

    public AircraftCarrier(Vector2d location, Facing facing)
    {
        this.location = location;
        this.facing = facing;

        drawables = new int[shipLength];
        drawables[0] = R.drawable.a1;
        drawables[1] = R.drawable.a2;
        drawables[2] = R.drawable.a3;
        drawables[3] = R.drawable.a4;
        drawables[4] = R.drawable.a5;

        for (int i = 0; i < shipLength; i++)
        {
            damage[i] = 0;
        }
    }

    @Override
    public WarshipType getType()
    {
        return type;
    }

    @Override
    public int getLength()
    {
        return shipLength;
    }

    @Override
    public int getFirepower()
    {
        return firepower;
    }

    @Override
    public Vector2d getLocation()
    {
        return location;
    }

    @Override
    public void setLocation(int x, int y) {
        this.location = new Vector2d(x, y);
    }

    @Override
    public Facing getFacing()
    {
        return this.facing;
    }

    @Override
    public void switchFacing() {
        if(this.facing == Facing.North)
            this.facing = Facing.West;
        else
            this.facing = Facing.North;
    }

    @Override
    public boolean isSunk()
    {
        return sunk;
    }

    @Override
    public void sink() {
        this.sunk = true;
    }

    @Override
    public boolean damage(int location)
    {
        if (damage[location] == 1)
            return false;
        else
            damage[location] = 1;

        if (checkSunk())
            return true;
        else
            return false;
    }

    @Override
    public boolean damage(Vector2d location) {
        return false;
    }

    private boolean checkSunk()
    {
        for (int i = 0; i < shipLength; i++)
        {
            if (damage[i] == 0)
                return false;
        }

        return true;
    }

    @Override
    public int[] getDrawables() {
        return drawables;
    }

}
