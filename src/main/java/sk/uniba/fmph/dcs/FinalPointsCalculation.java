package sk.uniba.fmph.dcs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FinalPointsCalculation {

    public FinalPointsCalculation() {

    }

    public Points getPoints(ArrayList<List<Optional<Tile>>> wall) {
        int completedRows = 0, completedColumns = 0, completedColors = 0;
        //number of completed rows
        for (int i = 0; i < wall.size(); i++) {
            int numberOfTilesInRow = 0;
            for (int j = 0; j < wall.get(i).size(); j++) {
                if (wall.get(i).get(j).isPresent()) {
                    numberOfTilesInRow++;
                }
            }
            if (numberOfTilesInRow == wall.size()) completedRows++;
        }
        //number of completed columns
        for (int i = 0; i < wall.size(); i++) {
            int numberOfTilesInColumn = 0;
            for (int j = 0; j < wall.get(i).size(); j++) {
                if (wall.get(j).get(i).isPresent()) numberOfTilesInColumn++;
            }
            if (numberOfTilesInColumn == wall.get(i).size()) completedColumns++;
        }
        //number of completed colors
        for (int colorIndex = 0; colorIndex < 5; colorIndex++) {
            int numberOfTheSameColorsInWall = 0;
            for (int i = 0; i < 5; i++) {
                int coll = (colorIndex + i) % 5;
                if (wall.get(i).get(coll).isPresent()) numberOfTheSameColorsInWall++;
            }
            if (numberOfTheSameColorsInWall == 5) completedColors++;
        }
        int totalPoints = completedRows * 2 + completedColumns * 7 + completedColors * 10;
        return new Points(totalPoints);
    }
}
