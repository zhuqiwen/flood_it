import java.util.ArrayList;
import java.util.List;

/**
 * Coord represents an (x,y)-coordinate on a 2D board. The origin, (0,0) is
 * assumed to be in the upper left corner. X-coordinates increase from left
 * to right. Y-coordinates increase from top to bottom. Operations are provided
 * to easily locate neighboring coordinates (in the NSEW compass directions)
 * on a board.
 *
 * @author Qiwen Zhu
 */

public class Coord implements Comparable<Coord> {
    /**
     *  The upper left corner of the board.
     */
    public static Coord ORIGIN = new Coord(0, 0);

    private int x, y;

    /**
     * Constructs a new Coord that is a copy of the given Coord.
     */
    public Coord(Coord coord) {
        this(coord.x, coord.y);
    }

    /**
     * Constructs a new Coord representing (x,y).
     */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the Coord that is directly above (i.e., north of) this one.
     */
    public Coord up()
    {
        return new Coord(x, y - 1);
    }

    /**
     * Returns the Coord that is directly below (i.e., south of) this one.
     */
    public Coord down()
    {
        return new Coord(x, y + 1);
    }

    /**
     * Returns the Coord that is immediately to the left (i.e., west)
     * of this one.
     */
    public Coord left()
    {
        return new Coord(x - 1, y);
    }

    /**
     * Returns the Coord that is immediately to the right (i.e., east)
     * of this one.
     */
    public Coord right()
    {
        return new Coord(x + 1, y);
    }



    /**
     * Returns true iff this Coord is visible on a board of the given size
     * by comparing coordinates with boundary of the board.
     * @param int size
     * @return boolean
     */
    public boolean onBoard(int size)
    {
        if(getX() < 0 || getY() < 0)
        {
            return false;
        }

        if(getX() >= size || getY() >= size)
        {
            return false;
        }

        return true;
    }


    /**
     * Returns a list of the immediate board coordinates of this Coord's north,
     * south, east, and west neighbors.
     * Only the Coord visible on the board will be added as neightbor; otherwise, add null.
     * neighbors' sequence
     * @param int size
     * @return List<Coord> neighbors
     */
    public List<Coord> neighbors(int size)
    {
        List<Coord> neighbors = new ArrayList<>(4);

        if(this.up().onBoard(size))
        {
            neighbors.add(this.up());
        }
        else
        {
            neighbors.add(null);
        }

        if(this.right().onBoard(size))
        {
            neighbors.add(this.right());
        }
        else
        {
            neighbors.add(null);
        }

        if(this.down().onBoard(size))
        {
            neighbors.add(this.down());
        }
        else
        {
            neighbors.add(null);
        }

        if(this.left().onBoard(size))
        {
            neighbors.add(this.left());
        }
        else
        {
            neighbors.add(null);
        }

        return neighbors;
    }

    /**
     * Returns true iff the (x,y)-coordinates of the given object match this Coord's
     * (x,y)-coordinates.
     */
    public boolean equals(Object obj) {
        if (obj instanceof Coord) {
            Coord that = (Coord) obj;
            return that.x == this.x && that.y == this.y;
        }
        return false;
    }

    /**
     * Coords are ordered first on the x-coordinate and then on the y-coordinate.
     */
    public int compareTo(Coord that) {
        if (this.x == that.x)
            return this.y - that.y;
        return this.x - that.x;
    }

    /**
     * Returns the x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate.
     */
    public int getY() {
        return y;
    }


    /**
     * Pre-hashing: pack this coordinate into an int, so that the key space is
     * as uniformly distributed among the range of integers as possible.
     *
     * *********
     * This piece of code is inspired by the source code in java.awt.geom.Point2D
     * *********
     * @return int
     */
    public int hashCode()
    {
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits ^= java.lang.Double.doubleToLongBits(getY()) * 31;
        return (((int) bits) ^ ((int) (bits >> 31)));
    }

    /**
     * Returns this Coord as a string of the form (x, y).
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Simple testing.
     */
    public static void main(String... args) {
        Coord someCoord = new Coord(2, 1);
        System.out.println("someCoord = " + someCoord);
        System.out.println("someCoord.hashCode() = " + someCoord.hashCode());
        System.out.println("someCoord.onBoard(4) = " + someCoord.onBoard(4));
        System.out.println("neighbors on a 3x3 board = " + someCoord.neighbors(3));
        System.out.println("neighbors on a 4x4 board = " + someCoord.neighbors(4));
        System.out.println();

        someCoord = ORIGIN;
        System.out.println("someCoord = " + someCoord);
        System.out.println("someCoord.hashCode() = " + someCoord.hashCode());
        System.out.println("someCoord.onBoard(3) = " + someCoord.onBoard(3));
        System.out.println("neighbors on a 3x3 board = " + someCoord.neighbors(3));
        System.out.println("neighbors on a 1x1 board = " + someCoord.neighbors(1));
        System.out.println();

        someCoord = new Coord(5, 5);
        System.out.println("someCoord = " + someCoord);
        System.out.println("someCoord.hashCode() = " + someCoord.hashCode());
        System.out.println("someCoord.onBoard(5) = " + someCoord.onBoard(5));
        System.out.println("neighbors on a 3x3 board = " + someCoord.neighbors(3));
        System.out.println("neighbors on a 6x6 board = " + someCoord.neighbors(6));
    }
}
