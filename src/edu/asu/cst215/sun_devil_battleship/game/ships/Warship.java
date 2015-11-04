/**
 * Created by Alexander on 10/29/2015.
 */

package edu.asu.cst215.sun_devil_battleship.game.ships;

import edu.asu.cst215.sun_devil_battleship.game.Facing;
import edu.asu.cst215.sun_devil_battleship.game.Vector2d;
import edu.asu.cst215.sun_devil_battleship.game.WarshipType;

public interface Warship
{
    WarshipType getType();

    int getLength();

    int getFirepower();

    Vector2d[] getLocation();

    Facing getFacing();

    boolean isSunk();

    boolean damage(int location);

    boolean damage(Vector2d location);
}
