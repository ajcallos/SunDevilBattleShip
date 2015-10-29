/**
 * Created by Alexander on 10/29/2015.
 */

package edu.asu.cst215.sun_devil_battleship.game.ships;

import edu.asu.cst215.sun_devil_battleship.game.Facing;
import edu.asu.cst215.sun_devil_battleship.game.Vector2d;
import edu.asu.cst215.sun_devil_battleship.game.WarshipType;

public class Battleship implements Warship
{
    private final int shipLength = 4;
    private final int firepower = 5;
    private final WarshipType type = WarshipType.Battleship;
    private final Facing facing;
    private final Vector2d location;

    private boolean sunk = false;
    private int[] damage = new int[shipLength];

    public Battleship(Vector2d location, Facing facing)
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
