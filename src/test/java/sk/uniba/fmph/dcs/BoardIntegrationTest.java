package sk.uniba.fmph.dcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BoardIntegrationTest {
    private Board board;
    private ArrayList<PatternLine> patternLines;
    private ArrayList<WallLine> wallLines;
    private Floor floor;

    @Before
    public void setUp() {
        ArrayList<Points> pointPattern = new ArrayList<>(List.of(new Points(-1), new Points(-1)));
        UsedTilesGiveInterface usedTiles = new UsedTiles();
        floor = new Floor(usedTiles, pointPattern);
        wallLines = new ArrayList<>();
        patternLines = new ArrayList<>();
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
        wallLine1.setLineDown(wallLine2);
        wallLine2.setLineUp(wallLine1);
        wallLine2.setLineDown(wallLine3);
        wallLine3.setLineUp(wallLine2);
        wallLine3.setLineDown(wallLine4);
        wallLine4.setLineUp(wallLine3);
        wallLine4.setLineDown(wallLine5);
        wallLine5.setLineUp(wallLine4);
        wallLines.addAll(new ArrayList<>(List.of(wallLine1, wallLine2, wallLine3, wallLine4, wallLine5)));
        patternLines.add(new PatternLine(floor, usedTiles, wallLine1, 1));
        patternLines.add(new PatternLine(floor, usedTiles, wallLine2, 2));
        patternLines.add(new PatternLine(floor, usedTiles, wallLine3, 3));
        patternLines.add(new PatternLine(floor, usedTiles, wallLine4, 4));
        patternLines.add(new PatternLine(floor, usedTiles, wallLine5, 5));
        board = new Board(patternLines, wallLines, floor);
    }

    @Test
    public void integrationTestBoard() {
        board.put(0, new ArrayList<>(List.of(Tile.YELLOW)));
        board.put(2, new ArrayList<>(List.of(Tile.YELLOW, Tile.YELLOW, Tile.YELLOW, Tile.YELLOW)));
        board.put(4, new ArrayList<>(List.of(Tile.BLUE, Tile.BLUE)));

        assertEquals("I", patternLines.get(0).state());
        assertEquals("", patternLines.get(1).state());
        assertEquals("III", patternLines.get(2).state());
        assertEquals("", patternLines.get(3).state());
        assertEquals("BB", patternLines.get(4).state());
        assertEquals("I", floor.state());
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals("I", wallLines.get(0).state());
        assertEquals("", wallLines.get(1).state());
        assertEquals("I", wallLines.get(2).state());
        assertEquals("", wallLines.get(3).state());
        assertEquals("", wallLines.get(4).state());
        assertEquals("floor is empty after round is finished.","", floor.state());
        assertEquals("+2 points for tiles but -1 for floor",1, board.points.getValue());

        board.put(0, new ArrayList<>(List.of(Tile.BLUE)));
        board.put(1, new ArrayList<>(List.of(Tile.GREEN)));
        board.put(4, new ArrayList<>(List.of(Tile.BLUE, Tile.BLUE)));

        assertEquals("B", patternLines.get(0).state());
        assertEquals("G", patternLines.get(1).state());
        assertEquals("", patternLines.get(2).state());
        assertEquals("", patternLines.get(3).state());
        assertEquals("BBBB", patternLines.get(4).state());
        assertEquals("", floor.state());
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals("BI", wallLines.get(0).state());
        assertEquals("", wallLines.get(1).state());
        assertEquals("I", wallLines.get(2).state());
        assertEquals("", wallLines.get(3).state());
        assertEquals("", wallLines.get(4).state());
        assertEquals("+2 points for linked horizontally",3, board.points.getValue());

        board.put(0, new ArrayList<>(List.of(Tile.BLACK)));
        board.put(1, new ArrayList<>(List.of(Tile.GREEN, Tile.GREEN)));
        board.put(4, new ArrayList<>(List.of(Tile.BLUE, Tile.BLUE)));

        assertEquals("L", patternLines.get(0).state());
        assertEquals("GG", patternLines.get(1).state());
        assertEquals("", patternLines.get(2).state());
        assertEquals("", patternLines.get(3).state());
        assertEquals("BBBBB", patternLines.get(4).state());
        assertEquals("GB", floor.state());
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals("BIL", wallLines.get(0).state());
        assertEquals("G", wallLines.get(1).state());
        assertEquals("I", wallLines.get(2).state());
        assertEquals("", wallLines.get(3).state());
        assertEquals("B", wallLines.get(4).state());
        assertEquals("+3 for horizontally linked +2 for 2 new tiles -2 for floor",
                6, board.points.getValue());

        board.put(0, new ArrayList<>(List.of(Tile.RED)));

        assertEquals("R", patternLines.get(0).state());
        assertEquals("", patternLines.get(1).state());
        assertEquals("", patternLines.get(2).state());
        assertEquals("", patternLines.get(3).state());
        assertEquals("", patternLines.get(4).state());
        assertEquals("", floor.state());
        assertEquals(FinishRoundResult.NORMAL, board.finishRound());
        assertEquals("BIRL", wallLines.get(0).state());
        assertEquals("G", wallLines.get(1).state());
        assertEquals("I", wallLines.get(2).state());
        assertEquals("", wallLines.get(3).state());
        assertEquals("B", wallLines.get(4).state());
        assertEquals("+4 for horizontally linked",10, board.points.getValue());

        board.put(0, new ArrayList<>(List.of(Tile.GREEN)));

        assertEquals("G", patternLines.get(0).state());
        assertEquals("", patternLines.get(1).state());
        assertEquals("", patternLines.get(2).state());
        assertEquals("", patternLines.get(3).state());
        assertEquals("", patternLines.get(4).state());
        assertEquals("", floor.state());
        assertEquals("We have 5 in a row so the game ends.",FinishRoundResult.GAME_FINISHED, board.finishRound());
        assertEquals("BIRLG", wallLines.get(0).state());
        assertEquals("G", wallLines.get(1).state());
        assertEquals("I", wallLines.get(2).state());
        assertEquals("", wallLines.get(3).state());
        assertEquals("B", wallLines.get(4).state());
        assertEquals("+5 for horizontally linked +2 for full row",17, board.points.getValue());
    }
}
