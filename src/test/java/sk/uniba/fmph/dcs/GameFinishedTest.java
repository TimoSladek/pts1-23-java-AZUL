package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class GameFinishedTest {
    private GameFinished gameFinished;
    private ArrayList<List<Optional<Tile>>> wall;

    @Before
    public void setUp() {
        gameFinished = new GameFinished();
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
        wallLine1.putTile(Tile.YELLOW);
        wallLine1.putTile(Tile.RED);
        wallLine1.putTile(Tile.BLACK);
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
    public void testGameFinished() {
        assertEquals("gameFinished should return NORMAL if row in wallLine is not full.",
                FinishRoundResult.NORMAL, gameFinished.gameFinished(wall));
        wall.get(0).set(4, Optional.of(Tile.GREEN));
        assertEquals("gameFinished should return GAME_FINISHED if row in wallLine is full.",
                FinishRoundResult.GAME_FINISHED, gameFinished.gameFinished(wall));
    }
}
