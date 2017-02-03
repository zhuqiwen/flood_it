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
    public void testneighbors() {
        assert "[(5, 4), (4, 5)]".equals(new Coord(5, 5).neighbors(6).toString());
    }

}