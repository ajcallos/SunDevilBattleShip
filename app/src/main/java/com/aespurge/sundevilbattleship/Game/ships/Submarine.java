/**
 * Created by Alexander on 10/29/2015.
 */

package com.aespurge.sundevilbattleship.Game.ships;

import com.aespurge.sundevilbattleship.Game.Facing;
import com.aespurge.sundevilbattleship.Game.Vector2d;
import com.aespurge.sundevilbattleship.Game.WarshipType;

public class Submarine implements Warship
{
    private final int shipLength = 2;
    private final int firepower = 2;
    private final WarshipType type = WarshipType.Submarine;
    private final Facing facing;
    private final Vector2d location;

    private boolean sunk = false;
    private int[] damage = new int[shipLength];

    public Submarine(Vector2d location, Facing facing)
    {
        this.location = location;
        this.facing = facing;

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
    public Facing getFacing()
    {
        return this.facing;
    }

    @Override
    public boolean isSunk()
    {
        return sunk;
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

    private boolean checkSunk()
    {
        for (int i = 0; i < shipLength; i++)
        {
            if (damage[i] == 0)
                return false;
        }

        return true;
    }
}
