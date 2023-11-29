package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.Objects;

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
        int count = 0;
        for (TileSource source : tileSources) {
            if (!source.isEmpty()){
                count++;
                if (!Objects.equals(source.state(), "S"))return false;
            }
            if (count == 2)return  false;
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
