package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameIntegrationTest {
    private Game game;
    private ArrayList<Board> boards;
    private TableArea tableArea;
    private FakeBag fakeBag;
    private TableCenter tableCenter;

    @Before
    public void setUp() {
        boards = new ArrayList<>();
        ArrayList<Points> pointPattern = new ArrayList<>(List.of(new Points(-1), new Points(-1), new Points(-2), new Points(-2), new Points(-2), new Points(-3), new Points(-3)));
        UsedTiles usedTiles = new UsedTiles();
        fakeBag = new FakeBag(new ArrayList<>(List.of(Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLACK, Tile.BLUE)));
        ArrayList<Tile> tileTypes1 = new ArrayList<>(List.of(Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.BLACK, Tile.GREEN));
        ArrayList<Tile> tileTypes2 = new ArrayList<>(List.of(Tile.GREEN, Tile.BLUE, Tile.YELLOW, Tile.RED, Tile.BLACK));
        ArrayList<Tile> tileTypes3 = new ArrayList<>(List.of(Tile.BLACK, Tile.GREEN, Tile.BLUE, Tile.YELLOW, Tile.RED));
        ArrayList<Tile> tileTypes4 = new ArrayList<>(List.of(Tile.RED, Tile.BLACK, Tile.GREEN, Tile.BLUE, Tile.YELLOW));
        ArrayList<Tile> tileTypes5 = new ArrayList<>(List.of(Tile.YELLOW, Tile.RED, Tile.BLACK, Tile.GREEN, Tile.BLUE));
        for (int i = 0; i < 2; i++) {
            Floor floor = new Floor(usedTiles, pointPattern);
            WallLine wallLine1 = new WallLine(tileTypes1);
            WallLine wallLine2 = new WallLine(tileTypes2);
            WallLine wallLine3 = new WallLine(tileTypes3);
            WallLine wallLine4 = new WallLine(tileTypes4);
            WallLine wallLine5 = new WallLine(tileTypes5);
            wallLine1.setLineDown(wallLine2);
            wallLine2.setLineUp(wallLine1);
            wallLine2.setLineDown(wallLine3);
            wallLine3.setLineUp(wallLine2);
            wallLine3.setLineDown(wallLine4);
            wallLine4.setLineUp(wallLine3);
            wallLine4.setLineDown(wallLine5);
            wallLine5.setLineUp(wallLine4);
            ArrayList<WallLine> wallLines = new ArrayList<>(new ArrayList<>(List.of(wallLine1, wallLine2, wallLine3, wallLine4, wallLine5)));
            ArrayList<PatternLine> patternLines = new ArrayList<>();
            for (int j = 1; j <= 5; j++) {
                patternLines.add(new PatternLine(floor, usedTiles, wallLines.get(j - 1), j));
            }
            boards.add(new Board(patternLines, wallLines, floor));
        }
        tableCenter = new TableCenter();
        ArrayList<TileSource> tileSources = new ArrayList<>();
        tileSources.add(tableCenter);
        for (int i = 0; i < 5; i++) {
            tileSources.add(new Factory(fakeBag, tableCenter));
        }
        tableArea = new TableArea(tileSources);
        GameObserver gameObserver = new GameObserver();
        game = new Game(gameObserver, tableArea, boards);
    }

    @Test
    public void integrationTestGame() {
        ArrayList<Tile> tiles = new ArrayList<>(List.of(Tile.YELLOW, Tile.GREEN, Tile.RED, Tile.BLUE, Tile.BLACK, Tile.YELLOW));
        for (int i = 0; i < 5; i++) {
            fakeBag.refill(tiles.get(i), tiles.get(i+1));
            for (int j = 0; j < 5; j++) {
                game.take(game.getCurrentPlayerId(), j + 1, 0, j);
            }
            boolean spTile = true;
            do {
                if (spTile) {
                    game.take(game.getCurrentPlayerId(), 0, 1, 0);
                    spTile = false;
                } else {
                    game.take(game.getCurrentPlayerId(), 0, 0, 0);
                }
            } while (tableArea.state().length() != 21);
        }
        assertEquals("In the end player 1 will have 0 points.",
                0, boards.get(0).points.getValue());
        assertEquals("And player 2 will have 9 points.",
                9, boards.get(1).points.getValue());

    }
}
