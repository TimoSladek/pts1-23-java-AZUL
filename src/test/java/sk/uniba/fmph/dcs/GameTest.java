package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private ArrayList<PatternLine> patternLines1;
    ArrayList<TileSource> tileSources;

    @Before
    public void setUp() {
        TableCenter tableCenter = new TableCenter();
        UsedTilesGiveInterface usedTiles = new UsedTiles();
        BagInterface fakeBag = new FakeBag(new ArrayList<>(
                List.of(Tile.YELLOW, Tile.YELLOW, Tile.BLUE, Tile.BLUE, Tile.YELLOW, Tile.YELLOW, Tile.BLUE, Tile.BLUE)));
        Factory factory = new Factory(fakeBag, tableCenter);
        tileSources = new ArrayList<>();
        tileSources.add(tableCenter);
        tileSources.add(factory);
        TableArea tableArea = new TableArea(tileSources);
        GameObserver gameObserver = new GameObserver();
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
        ArrayList<WallLine> wallLines = new ArrayList<>();
        wallLines.add(wallLine1);
        wallLines.add(wallLine2);
        wallLines.add(wallLine3);
        wallLines.add(wallLine4);
        wallLines.add(wallLine5);
        ArrayList<Points> pointPattern = new ArrayList<>(List.of(new Points(-1), new Points(-1)));
        Floor floor1 = new Floor(usedTiles, pointPattern);
        Floor floor2 = new Floor(usedTiles, pointPattern);
        patternLines1 = new ArrayList<>();
        ArrayList<PatternLine> patternLines2 = new ArrayList<>();
        patternLines1.add(new PatternLine(floor1, usedTiles, wallLine1, 1));
        patternLines1.add(new PatternLine(floor1, usedTiles, wallLine2, 2));
        patternLines1.add(new PatternLine(floor1, usedTiles, wallLine3, 3));
        patternLines1.add(new PatternLine(floor1, usedTiles, wallLine4, 4));
        patternLines1.add(new PatternLine(floor1, usedTiles, wallLine5, 5));
        patternLines2.add(new PatternLine(floor2, usedTiles, wallLine1, 1));
        patternLines2.add(new PatternLine(floor2, usedTiles, wallLine2, 2));
        patternLines2.add(new PatternLine(floor2, usedTiles, wallLine3, 3));
        patternLines2.add(new PatternLine(floor2, usedTiles, wallLine4, 4));
        patternLines2.add(new PatternLine(floor2, usedTiles, wallLine5, 5));
        Board board1 = new Board(patternLines1, wallLines, floor1);
        Board board2 = new Board(patternLines2, wallLines, floor2);
        game = new Game(gameObserver, tableArea, new ArrayList<>(List.of(board1, board2)));
    }

    @Test
    public void testGame() {
        assertEquals("When Game is created tableCenter should contain only Starting_Player tile.",
                "S", tileSources.get(0).state());
        assertEquals("And Factory should contain 2 yellow and 2 blue tiles.",
                "IIBB", tileSources.get(1).state());
        int startingPlayerId = game.getCurrentPlayerId();
        game.take(game.getCurrentPlayerId(), 1, 0, 1);
        assertEquals("When starting player takes yellow tiles form factory, factory should be empty.",
                "", tileSources.get(1).state());
        assertEquals("And patternLine2 of currentPlayer will hold 2 yellow tiles.",
                "II", patternLines1.get(1).state());
        assertEquals("And TableCenter will hold 2 blue tiles with Starting_Player tile.",
                "SBB", tileSources.get(0).state());
        assertNotEquals("After startingPlayer take call, currentPlayer is changed.",
                game.getCurrentPlayerId(), startingPlayerId);
        assertFalse("starting player can not take again when it is not his turn.",
                game.take(startingPlayerId, 0, 1, 1));
        int player2 = game.getCurrentPlayerId();
        game.take(game.getCurrentPlayerId(), 0, 1, 1);
        assertEquals("Now when currentPlayer will take tiles from tableCenter he should get Starting_Player tile and because round end," +
                        "he is still current player.",
                game.getCurrentPlayerId(), player2);
    }
}
