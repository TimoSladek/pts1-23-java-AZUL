package sk.uniba.fmph.dcs;

import java.util.ArrayList;

public class Game implements GameInterface {

    private final GameObserver gameObserver;
    private final TableArea tableArea;
    private Bag bag;
    private final ArrayList<Board> boards;
    private int currentPlayerId;
    private int startingPlayerId;

    public Game(GameObserver gameObserver, TableArea tableArea, Bag bag, ArrayList<Board> boards) {
        this.gameObserver = gameObserver;
        this.tableArea = tableArea;
        this.bag = bag;
        this.boards = boards;
        this.currentPlayerId = 0;
    }

    @Override
    public boolean take(int playerId, int sourceId, int idx, int destinationIdx) {
        if (playerId != currentPlayerId) {
            return false;
        }
        ArrayList<Tile> tiles = tableArea.take(sourceId, idx);
        if(tiles.contains(Tile.STARTING_PLAYER)){
            startingPlayerId = currentPlayerId;
        }
        boards.get(playerId).put(destinationIdx, tiles);
        if (tableArea.isRoundEnd()){
            currentPlayerId = startingPlayerId;
            for (Board board: boards){
                FinishRoundResult result = board.finishRound();
                if (result == FinishRoundResult.GAME_FINISHED){
                    int mostPoints = 0, winner = 0;
                    for (int i = 0; i < boards.size(); i++) {
                        boards.get(i).endGame();
                        if (boards.get(i).points.getValue() > mostPoints){
                            mostPoints = boards.get(i).points.getValue();
                            winner = i;
                        }
                    }
                    gameObserver.notifyEverybody("GAME_FINISHED AND THE WINNER IS PLAYER NUMBER: " + winner);
                    return true;
                }
            }
            tableArea.startNewRound();
        } else {
            currentPlayerId = (currentPlayerId + 1) % boards.size();
            gameObserver.notifyEverybody(boards.get(playerId).state());
        }
        return true;
    }
}
