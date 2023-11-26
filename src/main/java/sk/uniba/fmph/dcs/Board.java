package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Board {
    private ArrayList<PatternLine> patternLines;
    private ArrayList<WallLine> wallLines;
    private Floor floor;
    private FinalPointsCalculation finalPointsCalculation;
    private GameFinished gameFinished;
    private Points points;

    public Board(
            ArrayList<PatternLine> patternLines,
            ArrayList<WallLine> wallLines,
            Floor floor,
            FinalPointsCalculation finalPointsCalculation,
            GameFinished gameFinished
    ) {
        this.patternLines = patternLines;
        this.wallLines = wallLines;
        this.floor = floor;
        this.finalPointsCalculation = finalPointsCalculation;
        this.gameFinished = gameFinished;
        this.points = new Points(0);
    }

    public void put(int destinationIdx, ArrayList<Tile> tiles) {
        if (destinationIdx == -1) floor.put(tiles);
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
        FinishRoundResult result = gameFinished.gameFinished(wall);
        int roundPoints = points.getValue();
        for (PatternLine line : patternLines) {
            roundPoints += line.finishRound().getValue();
        }
        roundPoints += floor.finishRound().getValue();
        points = new Points(roundPoints);
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
