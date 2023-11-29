package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TableAreaIntegrationTest {

    private TableArea tableArea;
    private TableCenter tableCenter;

    @Before
    public void setUp() {
        ArrayList<TileSource> tileSources = new ArrayList<>();
        tableCenter = new TableCenter();
        FakeBag fakeBag = new FakeBag(new ArrayList<>(List.of(Tile.BLUE, Tile.BLUE, Tile.YELLOW, Tile.YELLOW)));
        Factory factory = new Factory(fakeBag, tableCenter);
        tileSources.add(tableCenter);
        tileSources.add(factory);
        tableArea = new TableArea(tileSources);
        fakeBag.refill(Tile.BLUE, Tile.BLACK);
    }

    @Test
    public void integrationTestTableArea() {
        assertEquals("TableArea should have 4 tiles in every factory plus Starting_Player tile in tableCenter.",
                5, tableArea.state().length());
        //throws IndexOutOfBounds if player tries to take Starting_player tile
        assertThrows(IndexOutOfBoundsException.class, () -> tableArea.take(0, 0));
        assertEquals("take call from factory should return 2 blue tiles",
                new ArrayList<>(List.of(Tile.BLUE, Tile.BLUE)),tableArea.take(1,1));
        assertEquals("And other tiles should be in tableCenter.",
                "SII", tableCenter.state());
        assertFalse("When at least one tileSource is not empty the round did not end.",
                tableArea.isRoundEnd());
        assertEquals("First take call from factory will also take Starting_Player tile.",
                new ArrayList<>(List.of(Tile.STARTING_PLAYER, Tile.YELLOW, Tile.YELLOW)), tableArea.take(0, 1));
        assertTrue("After there are no tiles to take from tableArea the round ends.",
                tableArea.isRoundEnd());
        tableArea.startNewRound();
        assertEquals("After startNewRound is called, all tileSources should be full again.",
                5, tableArea.state().length());
    }
}
