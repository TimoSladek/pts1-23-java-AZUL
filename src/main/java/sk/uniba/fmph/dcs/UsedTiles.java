package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Collection;

public class UsedTiles implements UsedTilesGiveInterface, UsedTilesTakeAllInterface {
    private ArrayList<Tile> usedTiles;

    public UsedTiles() {
        this.usedTiles = new ArrayList<>();
    }

    @Override
    public void give(Collection<Tile> ts) {
        ts.remove(Tile.STARTING_PLAYER);
        usedTiles.addAll(ts);
    }

    public Collection<Tile> takeAll() {
        ArrayList<Tile> tiles = new ArrayList<>(usedTiles);
        usedTiles.clear();
        return tiles;
    }

    public String state() {
        StringBuilder builder = new StringBuilder();
        for (Tile tile : usedTiles) {
            builder.append(tile.toString());
        }
        return builder.toString();
    }
}
