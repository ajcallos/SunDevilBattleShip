/**
 * Created by Alexander on 10/29/2015.
 */

package edu.asu.cst215.sun_devil_battleship.game.ships;

import edu.asu.cst215.sun_devil_battleship.game.Facing;
import edu.asu.cst215.sun_devil_battleship.game.Vector2d;
import edu.asu.cst215.sun_devil_battleship.game.WarshipType;

public interface Warship
{
    public WarshipType getType();

    public int getLength();

    public int getFirepower();

    public Vector2d getLocation();

    public Facing getFacing();

    public boolean isSunk();

    public boolean damage(int location);
}
