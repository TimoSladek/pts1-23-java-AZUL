package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BagTest {
    private Bag bag;
    private UsedTilesTakeAllInterface usedTiles;

    @Before
    public void setUp() {
        usedTiles = new UsedTiles();
        bag = new Bag(usedTiles);
    }

    @Test
    public void testBag() {
        assertEquals("Bag should should contain 100 tiles (20 tiles per color) when created.",
                "RRRRRRRRRRRRRRRRRRRRGGGGGGGGGGGGGGGGGGGGIIIIIIIIIIIIIIIIIIII" +
                        "BBBBBBBBBBBBBBBBBBBBLLLLLLLLLLLLLLLLLLLL", bag.state());
        bag.take(4);
        bag.take(5);
        assertEquals("After take call Factory should bag contain 100 - count tiles.",
                91, bag.state().length());
    }
}
