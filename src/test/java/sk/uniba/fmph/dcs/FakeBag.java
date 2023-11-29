package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;

public class FakeBag implements BagInterface {
    private ArrayList<Tile> tiles;

    public FakeBag(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    @Override
    public ArrayList<Tile> take(int count) {
        ArrayList<Tile> tilesToTake = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tilesToTake.add(tiles.remove(0));
        }
        return tilesToTake;
    }

    @Override
    public String state() {
        StringBuilder builder = new StringBuilder();
        for (Tile tile : tiles) {
            builder.append(tile.toString());
        }
        return builder.toString();
    }

    public void refill(Tile tile) {
        for (int i = 0; i < 20; i++) {
            tiles.add(tile);
        }
    }
}
