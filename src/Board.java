import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A Board represents the current state of the game. Boards know their dimension,
 * the collection of tiles that are inside the current flooded region, and those tiles
 * that are on the outside.
 *
 * @author <put your name here>
 */

public class Board {
    private Map<Coord, Tile> inside, outside;

    //use boarder to track tiles that has at least one neighbor in ourside.
    // boarder is a subset of inside, its size <= inside.size()
    private Map<Coord, Tile> border;
    private int size;

    /**
     * Constructs a square game board of the given size, initializes the list of
     * inside tiles to include just the tile in the upper left corner, and puts
     * all the other tiles in the outside list.
     */
    public Board(int size) {
        // A tile is either inside or outside the current flooded region.
        inside = new HashMap<>();
        outside = new HashMap<>();
        border = new HashMap<>();
        this.size = size;
        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++) {
                Coord coord = new Coord(x, y);
                outside.put(coord, new Tile(coord));
            }
        // Move the corner tile into the flooded region and run flood on its color.
        Tile corner = outside.remove(Coord.ORIGIN);
        inside.put(Coord.ORIGIN, corner);
        border.put(Coord.ORIGIN, corner);

        flood(corner.getColor());
    }

    /**
     * Returns the tile at the specified coordinate.
     */
    public Tile get(Coord coord) {
        if (outside.containsKey(coord))
            return outside.get(coord);
        return inside.get(coord);
    }

    /**
     * Returns the size of this board.
     */
    public int getSize() {
        return size;
    }


    /**
     * Returns true iff there is no element in outside region.
     * @return boolean
     */
    public boolean fullyFlooded()
    {
        return outside.size() == 0;
    }

    /**
     * TODO
     *
     * Updates this board by changing the color of the current flood region
     * and extending its reach.
     */
    public void flood(WaterColor color)
    {

//        this.get(Coord.ORIGIN).setColor(color);
////
//        expandInside(this.get(Coord.ORIGIN), color);

//



        //add edge element in inside to border
        maintainBorder(color);

        //prepare for iterate over the inside
        //use a deep copy of inside for the iteration, so that original inside can be change on the fly.
        Map<Coord, Tile> insideClone = new HashMap<>();
        insideClone.putAll(inside);

        Iterator kIt = insideClone.keySet().iterator();
        Tile tile;

        while (kIt.hasNext())
        {
            tile = insideClone.get(kIt.next());
            tile.setColor(color);

            for(Coord neighborCoord : tile.getCoord().neighbors(this.getSize()))
            {
                if(outside.containsKey(neighborCoord))
                {
                    if(this.get(neighborCoord).getColor() == color)
                    {
                        inside.put(neighborCoord, outside.remove(neighborCoord));
                        flood(color);
                    }
                }
            }
        }

    }

    private void maintainBorder(WaterColor color)
    {
        boolean allNeighborInside;
        for(Coord c: inside.keySet())
        {
            allNeighborInside = true;
            for(Coord neighborC : c.neighbors(this.getSize()))
            {
                if(outside.containsKey(neighborC) && this.get(neighborC).getColor() != color)
                {
                    border.put(c, this.get(c));
                    allNeighborInside = false;
                }
            }
            if(allNeighborInside)
            {
                border.remove(c,this.get(c));
            }
        }

//        System.out.println("inside size: " + inside.size());
//        System.out.println("border size: " + border.size());

    }

//    private void expandInside(Tile currentTitle, WaterColor selectedColor)
//    {
//
//        if(currentTitle.getColor() == this.get(Coord.ORIGIN).getColor())
//        {
//            currentTitle.setColor(selectedColor);
//
//            for(Coord neighborCoord : currentTitle.getCoord().neighbors(this.getSize()))
//            {
//                expandInside(this.get(neighborCoord), selectedColor);
//            }
//        }
//    }











    /**
     * TODO
     *
     * Explore a variety of algorithms for handling a flood. Use the same interface
     * as for flood above, but add an index so your methods are named flood1,
     * flood2, ..., and so on. Then, use the batchTest() tool in Driver to run game
     * simulations and then display a graph showing the run times of all your different
     * flood functions. Do not delete your flood code attempts. For each variety of
     * flood, including the one above that you eventually settle on, write a comment
     * that describes your algorithm in English. For those implementations that you
     * abandon, state your reasons.
     **/

    /**
     * This is an attempt to utilize a queue to streamly or non-recursively do the flood.
     * First, we setup an empty queue, and push the (0,0) into it.
     * Then, we retrieve the 1st element in the queue, set its color
     * Next, we check if this element's neighbors has the same color and is contained by outside;
     *          if so, we move the neighbor from outside to inside;
     * Last, we push such a neighbor into the queue,
     *          so that it can be set color and its neighbors can be checked
     *          if they have same color and are contained by outside
     * @param WaterColor color
     */
     public void flood1(WaterColor color)
     {
         Queue<Coord> q = new LinkedList<>();
         q.offer(Coord.ORIGIN);

         Tile tmpTile;
         while(!q.isEmpty())
         {
             tmpTile = inside.get(q.poll());
             tmpTile.setColor(color);

             for(Coord neighborCoord : tmpTile.getCoord().neighbors(this.getSize()))
             {
                 if(outside.containsKey(neighborCoord))
                 {
                     if(this.get(neighborCoord).getColor() == color)
                     {
                         inside.put(neighborCoord, outside.remove(neighborCoord));
                         q.offer(neighborCoord);
                     }
                 }
             }
         }



     }

     public void flood2(WaterColor color) {

     }


    /**
     * TODO
     *
     * Returns the "best" GameColor for the next move.
     *
     * Modify this comment to describe your algorithm. Possible strategies to pursue
     * include maximizing the number of tiles in the current flooded region, or maximizing
     * the size of the perimeter of the current flooded region.
     */
    public WaterColor suggest()
    {
        return soberSuggest();
    }

    private WaterColor soberSuggest()
    {
        Iterator bIt = border.keySet().iterator();
        WaterColor result = WaterColor.pickOne();
        WaterColor tmp;

        int cnt = 0;


        while (bIt.hasNext())
        {
            // This could be further improved by using a helper to get a list/array of neighbors' colors
            List<Coord> neighbors = border.get(bIt.next()).getCoord().neighbors(this.getSize());
            tmp = this.get(neighbors.get(0)).getColor();
            if(cnt == 0)
            {
                result = tmp;
                cnt = 1;
            }
            else if(result == tmp)
            {
                cnt --;
            }
            else
            {
                cnt ++;
            }
        }

        return result;
    }


    /**
     * Returns a string representation of this board. Tiles are given as their
     * color names, with those inside the flooded region written in uppercase.
     */
    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                Coord curr = new Coord(x, y);
                WaterColor color = get(curr).getColor();
                ans.append(inside.containsKey(curr) ? color.toString().toUpperCase() : color);
                ans.append("\t");
            }
            ans.append("\n");
        }
        return ans.toString();
    }

    /**
     * Simple testing.
     */
    public static void main(String... args) {
        // Print out boards of size 1, 2, ..., 5
        int n = 5;
        for (int size = 1; size <= n; size++) {
            Board someBoard = new Board(size);
            System.out.println(someBoard);
        }
    }
}
