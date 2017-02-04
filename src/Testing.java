import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit tests for all TODO methods.
 */

public class Testing {

    @Test
    public void testOnBoard() {
        assertFalse(new Coord(3, 4).onBoard(4));
        assertTrue(new Coord(3, 4).onBoard(5));
    }

    @Test
    public void testNeighbors() {
        assert "[(5, 4), (4, 5)]".equals(new Coord(5, 5).neighbors(6).toString());
        assert "[(1, 0), (0, 1)]".equals(new Coord(0, 0).neighbors(6).toString());
        assert "[(2, 1), (3, 2), (2, 3), (1, 2)]".equals(new Coord(2, 2).neighbors(5).toString());
    }

    @Test
    public  void  testHashCode()
    {
        assert new Coord(0, 0).hashCode() == 0;
        assert new Coord(0, 5).hashCode() == -1033109504;
        assert new Coord(5, 0).hashCode() == 1075052544;
        assert new Coord(2, 2).hashCode() == -2147483648;

    }



}