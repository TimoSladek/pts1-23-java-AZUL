package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class FinalPointsCalculationTest {
    private FinalPointsCalculation finalPointsCalculation;
    private ArrayList<List<Optional<Tile>>> wall;

    @Before
    public void setUp() {
        finalPointsCalculation = new FinalPointsCalculation();
        ArrayList<Tile> tileTypes1 = new ArrayList<>(List.of(Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.BLACK, Tile.GREEN));
        ArrayList<Tile> tileTypes2 = new ArrayList<>(List.of(Tile.GREEN, Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.BLACK));
        ArrayList<Tile> tileTypes3 = new ArrayList<>(List.of(Tile.BLACK, Tile.GREEN, Tile.BLUE, Tile.YELLOW, Tile.RED));
        ArrayList<Tile> tileTypes4 = new ArrayList<>(List.of(Tile.RED, Tile.BLACK, Tile.GREEN, Tile.BLUE, Tile.YELLOW));
        ArrayList<Tile> tileTypes5 = new ArrayList<>(List.of(Tile.YELLOW, Tile.RED, Tile.BLACK, Tile.GREEN, Tile.BLUE));
        WallLine wallLine1 = new WallLine(tileTypes1);
        WallLine wallLine2 = new WallLine(tileTypes2);
        WallLine wallLine3 = new WallLine(tileTypes3);
        WallLine wallLine4 = new WallLine(tileTypes4);
        WallLine wallLine5 = new WallLine(tileTypes5);
        wallLine1.putTile(Tile.BLUE);
        wallLine2.putTile(Tile.BLUE);
        wallLine3.putTile(Tile.BLUE);
        wallLine4.putTile(Tile.BLUE);
        wallLine5.putTile(Tile.BLUE);
        wallLine2.putTile(Tile.GREEN);
        wallLine3.putTile(Tile.BLACK);
        wallLine4.putTile(Tile.RED);
        wallLine5.putTile(Tile.YELLOW);
        ArrayList<WallLine> wallLines = new ArrayList<>();
        wallLines.add(wallLine1);
        wallLines.add(wallLine2);
        wallLines.add(wallLine3);
        wallLines.add(wallLine4);
        wallLines.add(wallLine5);
        wall = new ArrayList<>();
        for (WallLine wallLine : wallLines) {
            wall.add(wallLine.getTiles());
        }
    }

    @Test
    public void testFinalPointsCalculation() {
        assertEquals("getPoints should return points from the wall(1 full color + 1 full column).",
                new Points(17), finalPointsCalculation.getPoints(wall));
    }
}
