/**
 * Created by Alexander on 10/29/2015.
 */

package com.aespurge.sundevilbattleship.game.ships;

import edu.asu.cst215.sun_devil_battleship.game.Facing;
import com.aespurge.sundevilbattleship.game.Vector2d;
import com.aespurge.sundevilbattleship.game.WarshipType;

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
