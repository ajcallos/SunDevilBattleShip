/**
 * Created by Alexander on 10/29/2015.
 */

package edu.asu.cst215.sun_devil_battleship.game;

public class Grid
{
    private WarshipType[][] grid;

    public Grid(int x, int y)
    {
        this.grid = new WarshipType[y][x];
    }

    public int getHeight()
    {
        return grid.length;
    }

    public int getWidth()
    {
        return grid[0].length;
    }

    public WarshipType getElement(int x, int y)
    {
        return grid[y][x];
    }

    public boolean placeElement(int x, int y, WarshipType warship)
    {
        if (grid[y][x] != null)
            return false;

        grid[y][x] = warship;

        return true;
    }


    // Trivial main method for testing purposes
    public static void main(String[] args)
    {
        Grid myGrid = new Grid(10, 10);

        myGrid.placeElement(5,4, WarshipType.Submarine);
        myGrid.placeElement(5,5, WarshipType.Submarine);

        myGrid.placeElement(1,0, WarshipType.AircraftCarrier);
        myGrid.placeElement(2,0, WarshipType.AircraftCarrier);
        myGrid.placeElement(3,0, WarshipType.AircraftCarrier);
        myGrid.placeElement(4,0, WarshipType.AircraftCarrier);

        for (int y = 0; y < myGrid.getHeight(); y++)
        {
            for (int x = 0; x < myGrid.getWidth(); x++)
            {
                WarshipType type = myGrid.getElement(x, y);

                String occupant = "";

                if (type != null)
                    occupant = "*";

                if (x == 0)
                    System.out.print("[");

                System.out.printf("%2s ", occupant);

                if (x == myGrid.getWidth() - 1)
                    System.out.print("]\n");
            }
        }
    }
}
