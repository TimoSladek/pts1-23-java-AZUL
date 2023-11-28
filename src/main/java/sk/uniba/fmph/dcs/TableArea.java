package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class TableArea {
    private final ArrayList<TileSource> tileSources;

    public TableArea(ArrayList<TileSource> tileSources) {
        this.tileSources = tileSources;
    }

    public ArrayList<Tile> take(int sourceIdx, int idx) {
        if (sourceIdx < 0 || sourceIdx >= tileSources.size()) {
            throw new IndexOutOfBoundsException("Invalid source index");
        }
        return tileSources.get(sourceIdx).take(idx);
    }

    public boolean isRoundEnd() {
        for (TileSource source : tileSources) {
            if (!source.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void startNewRound() {
        for (TileSource source : tileSources) {
            source.startNewRound();
        }
    }

    public String state() {
        StringBuilder builder = new StringBuilder();
        for (TileSource source : tileSources) {
            builder.append(source.state());
        }
        return builder.toString();
    }
}
