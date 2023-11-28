package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class PatternLine {
    private final ArrayList<Tile> patternLineTiles;
    private final Floor floor;
    private final UsedTilesGiveInterface usedTiles;
    private final WallLine wallLine;
    private final int capacity;

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
            int numberOfAddedTiles = capacity - patternLineTiles.size();
            for (int i = 0; i < numberOfAddedTiles; i++) {
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
