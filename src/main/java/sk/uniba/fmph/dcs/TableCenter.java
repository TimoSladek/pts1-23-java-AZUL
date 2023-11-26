package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class TableCenter implements TileSource {
    private ArrayList<Tile> tiles;

    public TableCenter() {
        this.tiles = new ArrayList<>();
        tiles.add(Tile.STARTING_PLAYER);
    }

    @Override
    public ArrayList<Tile> take(int idx) {
        if (idx < 0 || idx >= tiles.size()) throw new IndexOutOfBoundsException();
        ArrayList<Tile> takenTiles = new ArrayList<>();
        Tile chosenTile = tiles.get(idx);
        if (tiles.get(0) == Tile.STARTING_PLAYER) {
            takenTiles.add(tiles.remove(0));
        }
        for (Tile tile : tiles) {
            if (tile == chosenTile) tiles.add(tile);
        }
        tiles.removeIf(t -> t == chosenTile);
        return takenTiles;
    }

    @Override
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    @Override
    public void startNewRound() {
        tiles = new ArrayList<>();
        tiles.add(Tile.STARTING_PLAYER);
    }

    @Override
    public String state() {
        StringBuilder builder = new StringBuilder();
        for (Tile tile : tiles) {
            builder.append(tile.toString());
        }
        return builder.toString();
    }

    public void add(ArrayList<Tile> tiles) {
        this.tiles.addAll(tiles);
    }
}
