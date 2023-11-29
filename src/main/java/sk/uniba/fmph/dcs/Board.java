package sk.uniba.fmph.dcs;

import java.util.*;

public class Board {
    private final ArrayList<PatternLine> patternLines;
    private final ArrayList<WallLine> wallLines;
    private final Floor floor;
    private final FinalPointsCalculation finalPointsCalculation;
    private final GameFinished gameFinished;
    protected Points points;

    public Board(
            ArrayList<PatternLine> patternLines,
            ArrayList<WallLine> wallLines,
            Floor floor
    ) {
        this.patternLines = patternLines;
        this.wallLines = wallLines;
        this.floor = floor;
        this.points = new Points(0);
        this.finalPointsCalculation = new FinalPointsCalculation();
        this.gameFinished = new GameFinished();
    }

    public void put(int destinationIdx, ArrayList<Tile> tiles) {
        if (destinationIdx == -1) {
            floor.put(tiles);
            return;
        }
        if (tiles.get(0) == Tile.STARTING_PLAYER) {
            floor.put(Collections.singleton(tiles.remove(0)));
        }
        patternLines.get(destinationIdx).put(tiles);
    }

    public FinishRoundResult finishRound() {
        ArrayList<List<Optional<Tile>>> wall = new ArrayList<>();
        for (WallLine wallLine : wallLines) {
            wall.add(wallLine.getTiles());
        }
        int currentPoints = points.getValue();
        for (PatternLine line : patternLines) {
            currentPoints += line.finishRound().getValue();
        }
        currentPoints += floor.finishRound().getValue();
        if (currentPoints < 0) points = new Points(0);
        else points = new Points(currentPoints);
        FinishRoundResult result = gameFinished.gameFinished(wall);
        if (result == FinishRoundResult.GAME_FINISHED) {
            endGame();
        }
        return result;
    }

    public void endGame() {
        ArrayList<List<Optional<Tile>>> wall = new ArrayList<>();
        for (WallLine wallLine : wallLines) {
            wall.add(wallLine.getTiles());
        }
        int totalPoints = points.getValue() + finalPointsCalculation.getPoints(wall).getValue();
        this.points = new Points(totalPoints);
    }

    public String state() {
        StringBuilder builder = new StringBuilder();
        for (PatternLine patternLine : patternLines) {
            builder.append(patternLine.state());
        }
        for (WallLine wallLine : wallLines) {
            builder.append(wallLine.state());
        }
        builder.append(floor.state());
        return builder.toString();
    }
}
