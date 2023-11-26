package sk.uniba.fmph.dcs;

import java.util.*;

public class WallLine {
    private ArrayList<Tile> tileTypes;
    private WallLine lineUp;
    private WallLine lineDown;
    private List<Optional<Tile>> wallLineTiles;

    public WallLine(List<Tile> tileTypes) {
        this.tileTypes = new ArrayList<>(tileTypes);
        this.wallLineTiles = new ArrayList<>();
        for (int i = 0; i < tileTypes.size(); i++) {
            wallLineTiles.add(Optional.empty());
        }
    }

    public void setLineUp(WallLine lineUp) {
        this.lineUp = lineUp;
    }

    public void setLineDown(WallLine lineDown) {
        this.lineDown = lineDown;
    }

    public boolean canPutTile(Tile tile) {
        return tileTypes.contains(tile) && wallLineTiles.get(tileTypes.indexOf(tile)).isEmpty();
    }

    public List<Optional<Tile>> getTiles() {
        return wallLineTiles;
    }

    public Points putTile(Tile tile) {
        if (canPutTile(tile)) {
            int index = tileTypes.indexOf(tile);
            wallLineTiles.set(index, Optional.of(tile));
            return calculatePoints(index);
        }
        return new Points(0);
    }

    public String state() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < wallLineTiles.size(); i++) {
            if (wallLineTiles.get(i).isPresent()) {
                builder.append(tileTypes.get(i).toString());
            }
        }
        return builder.toString();
    }

    private Points calculatePoints(int index) {
        int points = 0, helper = 1;
        while (index - helper >= 0) {
            if (wallLineTiles.get(index - helper).isPresent()) {
                points++;
                helper++;
            } else {
                break;
            }
        }
        helper = 1;
        while (index + helper < wallLineTiles.size()) {
            if (wallLineTiles.get(index + helper).isPresent()) {
                points++;
                helper++;
            } else {
                break;
            }
        }

        if (points > 0) points++;
        int currentPoints = points;

        WallLine thisWallLine = this;
        while (thisWallLine.lineDown != null) {
            if (thisWallLine.lineDown.wallLineTiles.get(index).isPresent()) {
                points++;
                thisWallLine = thisWallLine.lineDown;
            } else {
                break;
            }
        }
        thisWallLine = this;
        while (thisWallLine.lineUp != null) {
            if (thisWallLine.lineUp.wallLineTiles.get(index).isPresent()) {
                points++;
                thisWallLine = thisWallLine.lineUp;
            } else {
                break;
            }
        }
        if (points > currentPoints) points++;
        return new Points(points);
    }
}
