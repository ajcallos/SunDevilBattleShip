/**
 * Created by Alexander on 10/29/2015.
 */

package com.aespurge.sundevilbattleship.Game.ships;

import com.aespurge.sundevilbattleship.Game.Facing;
import com.aespurge.sundevilbattleship.Game.Vector2d;
import com.aespurge.sundevilbattleship.Game.WarshipType;

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
