package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UsedTilesTest {
    private UsedTiles usedTiles;

    @Before
    public void setUp() {
        usedTiles = new UsedTiles();
    }

    @Test
    public void testUsedTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();
        assertEquals("UsedTiles should be empty when created.", "", usedTiles.state());
        tiles.addAll(List.of(Tile.BLUE, Tile.BLUE, Tile.BLUE, Tile.YELLOW, Tile.YELLOW, Tile.STARTING_PLAYER));
        usedTiles.give(tiles);
        assertEquals("UsedTiles should contain tiles we give it but without Starting_player.",
                "BBBII", usedTiles.state());
        usedTiles.takeAll();
        assertEquals("UsedTiles should be empty after we call takeAll.", "", usedTiles.state());
    }
}
