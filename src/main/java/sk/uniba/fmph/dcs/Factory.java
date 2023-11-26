package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class Factory implements TileSource {
    private Bag bag;
    private TableCenter tableCenter;
    private ArrayList<Tile> factoryTiles;

    public Factory(Bag bag, TableCenter tableCenter) {
        this.bag = bag;
        this.tableCenter = tableCenter;
        this.factoryTiles = new ArrayList<>();
    }

    @Override
    public ArrayList<Tile> take(int idx) {
        if (idx < 0 || idx >= factoryTiles.size()) throw new IndexOutOfBoundsException();
        ArrayList<Tile> takenTiles = new ArrayList<>();
        Tile chosenTile = factoryTiles.get(idx);
        for (Tile tile : factoryTiles) {
            if (tile == chosenTile) takenTiles.add(tile);
        }
        factoryTiles.removeIf(t -> t == chosenTile);
        tableCenter.add(factoryTiles);
        factoryTiles.clear();
        return takenTiles;
    }

    @Override
    public boolean isEmpty() {
        return factoryTiles.isEmpty();
    }

    @Override
    public void startNewRound() {
        factoryTiles.addAll(bag.take(4));
    }

    @Override
    public String state() {
        StringBuilder builder = new StringBuilder();
        for (Tile tile : factoryTiles) {
            builder.append(tile.toString());
        }
        return builder.toString();
    }
}

