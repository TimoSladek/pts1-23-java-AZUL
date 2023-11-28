package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameFinished {
    public GameFinished() {
    }

    public FinishRoundResult gameFinished(ArrayList<List<Optional<Tile>>> wall) {
        for (List<Optional<Tile>> row : wall) {
            int rowTileCount = 0;
            for (Optional<Tile> tile : row) {
                if (tile.isPresent()) rowTileCount++;
            }
            if (rowTileCount == row.size()) return FinishRoundResult.GAME_FINISHED;
        }
        return FinishRoundResult.NORMAL;
    }
}
