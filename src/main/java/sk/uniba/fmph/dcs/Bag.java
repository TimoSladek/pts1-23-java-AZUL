package sk.uniba.fmph.dcs;

import java.util.*;

public class Bag implements BagInterface{
    private final UsedTilesTakeAllInterface usedTiles;
    private final ArrayList<Tile> bagTiles;

    public Bag(UsedTilesTakeAllInterface usedTiles) {
        this.usedTiles = usedTiles;
        this.bagTiles = new ArrayList<>();
        for (Tile tile : List.of(Tile.RED, Tile.GREEN, Tile.YELLOW, Tile.BLUE, Tile.BLACK)) {
            for (int i = 0; i < 20; i++) {
                bagTiles.add(tile);
            }
        }
    }
    @Override
    public ArrayList<Tile> take(int count) {
        bagTiles.addAll(usedTiles.takeAll());
        ArrayList<Tile> tilesToTake = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tilesToTake.add(takeRandomTile());
        }
        return tilesToTake;
    }
    @Override
    public String state() {
        StringBuilder builder = new StringBuilder();
        for (Tile tile : bagTiles) {
            builder.append(tile.toString());
        }
        return builder.toString();
    }

    private Tile takeRandomTile() {
        Random rand = new Random();
        Tile tile = bagTiles.get(rand.nextInt(bagTiles.size()));
        bagTiles.remove(tile);
        return tile;
    }
}

