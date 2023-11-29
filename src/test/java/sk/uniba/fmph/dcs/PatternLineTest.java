package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PatternLineTest {
    private PatternLine patternLine;
    private Floor floor;

    @Before
    public void setUp() {
        UsedTilesGiveInterface usedTiles = new UsedTiles();
        ArrayList<Points> pointPattern = new ArrayList<Points>();
        pointPattern.add(new Points(1));
        pointPattern.add(new Points(2));
        pointPattern.add(new Points(2));
        floor = new Floor(usedTiles, pointPattern);
        ArrayList<Tile> tileTypes = new ArrayList<>(List.of(Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.BLACK, Tile.GREEN));
        WallLine wallLine = new WallLine(tileTypes);
        patternLine = new PatternLine(floor, usedTiles, wallLine, 4);
    }

    @Test
    public void testPatternLine() {
        assertEquals("PatternLine should be empty when created.",
                "", patternLine.state());
        patternLine.put(new ArrayList<>(List.of(Tile.YELLOW, Tile.YELLOW)));
        assertEquals("After we put 2 yellow tiles on the empty pattern line there should be 2 yellow tiles.",
                "II", patternLine.state());
        patternLine.put(new ArrayList<>(List.of(Tile.BLACK)));
        assertEquals("when we try put on the patternLine tile of another color, the tile should end up on the floor.",
                "L", floor.state());
        patternLine.put(new ArrayList<>(List.of(Tile.YELLOW, Tile.YELLOW, Tile.YELLOW)));
        assertEquals("when we put more tiles on patternLine then free capacity, patternLine will get full.",
                "IIII", patternLine.state());
        assertEquals("And the rest of tiles will go to floor.", "LI", floor.state());
        assertEquals("FinishRound will get us back points, if our patternLine is full",
                new Points(1), patternLine.finishRound());
        assertEquals("And patternLine after finishRound call should be empty.",
                "", patternLine.state());
        assertEquals("If capacity is not full, the finishRound will get us back 0 points.",
                new Points(0), patternLine.finishRound());
    }
}
