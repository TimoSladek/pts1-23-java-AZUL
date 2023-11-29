package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FactoryTest {
    private Factory factory;
    private FakeBag fakeBag;

    @Before
    public void setUp() {
        fakeBag = new FakeBag(new ArrayList<>(List.of(Tile.YELLOW, Tile.YELLOW, Tile.BLACK, Tile.RED)));
        TableCenter tableCenter = new TableCenter();
        factory = new Factory(fakeBag, tableCenter);
    }

    @Test
    public void testFactory() {
        assertEquals("Factory should contain 4 random tiles from bag when created.",
                4, factory.state().length());
        assertEquals("take call on index 1 will take all yellow tiles and rest of them sends to tableCenter",
                new ArrayList<>(List.of(Tile.YELLOW, Tile.YELLOW)), factory.take(1));
        assertTrue("After take call a factory should be empty.", factory.isEmpty());
        fakeBag.refill();
        factory.startNewRound();
        assertEquals("Factory should contain 4 random tiles from bag when round starts.",
                4, factory.state().length());
    }
}
