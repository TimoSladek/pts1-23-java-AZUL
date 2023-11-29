package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    private Board board;
    private Floor floor;
    private final ArrayList<PatternLine> patternLines = new ArrayList<>();
    private final ArrayList<WallLine> wallLines = new ArrayList<>();


    @Before
    public void setUp() {
        ArrayList<Points> pointPattern = new ArrayList<>(List.of(new Points(-1), new Points(-1)));
        UsedTilesGiveInterface usedTiles = new UsedTiles();
        floor = new Floor(usedTiles, pointPattern);
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
        wallLines.add(wallLine1);
        wallLines.add(wallLine2);
        wallLines.add(wallLine3);
        wallLines.add(wallLine4);
        wallLines.add(wallLine5);
        patternLines.add(new PatternLine(floor, usedTiles, wallLine1, 1));
        board = new Board(patternLines, wallLines, floor);
    }

    @Test
    public void testBoard() {
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(Tile.STARTING_PLAYER);
        tiles.add(Tile.YELLOW);
        tiles.add(Tile.YELLOW);
        board.put(0, tiles);
        assertEquals("If tiles contains Starting_Player tile, that tile goes to floor with tiles that does not fit in patternLine.",
                "SI", floor.state());
        assertEquals("the rest of them goes to patternLine.",
                "I", patternLines.get(0).state());
        board.put(-1, tiles);
        assertEquals("when idx is -1 tiles go to the floor.", "SII", floor.state());
        board.finishRound();
        assertEquals("After finishRound call, floor gets emptied.", "", floor.state());
        assertEquals("And points are calculated, we have 0(+1 for tile -1 for floor).",

                0, board.points.getValue());
        for (Tile tile : List.of(Tile.RED, Tile.GREEN, Tile.BLUE)) {
            board.put(0, new ArrayList<>(List.of(tile)));
            board.finishRound();
        }
        board.put(0, new ArrayList<>(List.of(Tile.BLACK)));
        assertEquals("When we get five tiles in a row in wallLine, the game is ginished.",
                FinishRoundResult.GAME_FINISHED, board.finishRound());
        assertEquals("", patternLines.get(0).state());
    }
}
