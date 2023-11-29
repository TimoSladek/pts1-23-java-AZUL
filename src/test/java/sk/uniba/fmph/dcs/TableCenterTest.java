package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TableCenterTest {
    private TableCenter tableCenter;

    @Before
    public void setUp() {
        tableCenter = new TableCenter();
    }

    @Test
    public void testTableCenter() {
        assertEquals("TableCenter should contain Starting_Player tile when created.",
                "S", tableCenter.state());
        ArrayList<Tile> tiles = new ArrayList<>(List.of(Tile.GREEN, Tile.GREEN, Tile.GREEN));
        tiles.addAll(List.of(Tile.RED, Tile.RED));
        tableCenter.add(tiles);
        assertEquals("TableCenter should contain all tiles that were add.",
                "SGGGRR", tableCenter.state());
        assertEquals("First take call should return 3 tiles of color green with Starting_Player tile.",
                new ArrayList<>(List.of(Tile.STARTING_PLAYER, Tile.GREEN, Tile.GREEN, Tile.GREEN)), tableCenter.take(1));
        assertEquals("After first take call TableCenter should contain tiles without" +
                        " Starting_Player tile and tiles of the same color that were taken.",
                "RR", tableCenter.state());
        tableCenter.startNewRound();
        assertEquals("After startNewRound is called, tableCenter should contain only Starting_Player tile.",
                "S", tableCenter.state());
    }
}
