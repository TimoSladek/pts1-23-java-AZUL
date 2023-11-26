package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class PatternLine {
    private ArrayList<Tile> patternLineTiles;
    private Floor floor;
    private UsedTilesGiveInterface usedTiles;
    private WallLine wallLine;
    private int capacity;

    public PatternLine(Floor floor, UsedTilesGiveInterface usedTiles, WallLine wallLine, int capacity) {
        this.floor = floor;
        this.usedTiles = usedTiles;
        this.wallLine = wallLine;
        this.capacity = capacity;
        this.patternLineTiles = new ArrayList<>();
    }

    public void put(ArrayList<Tile> tiles) {
        if (!patternLineTiles.isEmpty() && patternLineTiles.get(0) != tiles.get(0)) {
            floor.put(tiles);
            return;
        }
        if (tiles.size() > capacity - patternLineTiles.size()) {
            int numberOfaddedTiles = capacity - patternLineTiles.size();
            for (int i = 0; i < numberOfaddedTiles; i++) {
                patternLineTiles.add(tiles.remove(0));
            }
            floor.put(tiles);
        } else {
            patternLineTiles.addAll(tiles);
        }
    }

    public Points finishRound() {
        int totalPoints = 0;
        if (capacity == patternLineTiles.size()) {
            Points wallLinePoints = wallLine.putTile(patternLineTiles.remove(0));
            totalPoints += wallLinePoints.getValue();
            usedTiles.give(patternLineTiles);
            patternLineTiles.clear();
        }
        return new Points(totalPoints);
    }

    public String state() {
        StringBuilder builder = new StringBuilder();
        for (Tile tile : patternLineTiles) {
            builder.append(tile.toString());
        }
        return builder.toString();
    }
}
