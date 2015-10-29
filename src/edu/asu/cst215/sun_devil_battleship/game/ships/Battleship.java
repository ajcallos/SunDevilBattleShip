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

    public Battleship(Vector2d location, Facing facing)
    {
        this.location = location;
        this.facing = facing;
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
    public Facing getFacing()
    {
        return this.facing;
    }

    @Override
    public boolean isSunk()
    {
        return false;
    }

    @Override
    public boolean damage(Vector2d shot)
    {
        return false;
    }
    
}
