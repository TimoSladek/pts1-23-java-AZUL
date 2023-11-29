package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class WallLineTest {

    private WallLine wallLine;

    @Before
    public void setUp() {
        ArrayList<Tile> tileTypes = new ArrayList<>(List.of(Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.BLACK, Tile.GREEN));
        wallLine = new WallLine(tileTypes);
    }

    @Test
    public void testWallLine() {
        assertEquals("WallLine should be empty when created.", "", wallLine.state());
        assertTrue("canPutTile should return true if tileTypes contain tile and" +
                " wallLine does not already have this tileType.", wallLine.canPutTile(Tile.BLACK));
        List<Optional<Tile>> tiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tiles.add(Optional.empty());
        }
        assertEquals("getTiles should return List of Optional.empty if wallLine is empty.",
                tiles, wallLine.getTiles());
        wallLine.putTile(Tile.YELLOW);
        assertFalse("canPutTile should return false if wallLine already has given tile",
                wallLine.canPutTile(Tile.YELLOW));
        Points points = wallLine.putTile(Tile.RED);
        assertEquals("putTile should return number of points for putting given tile",
                new Points(2), points);
        assertEquals("putTile should return 0 points if given tile is already in wallLine",
                new Points(0), wallLine.putTile(Tile.YELLOW));
        wallLine.putTile(Tile.BLUE);
        tiles.set(0, Optional.of(Tile.BLUE));
        tiles.set(2, Optional.of(Tile.RED));
        tiles.set(1, Optional.of(Tile.YELLOW));
        assertEquals("getTiles should return List of tiles in wallLine.",
                tiles, wallLine.getTiles());
        assertEquals("WallLine should contain tiles in order of tileTypes.",
                "BIR", wallLine.state());
    }
}