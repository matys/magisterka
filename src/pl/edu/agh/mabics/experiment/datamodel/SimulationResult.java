package pl.edu.agh.mabics.experiment.datamodel;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 13.02.13
 * Time: 18:11
 */
public class SimulationResult {

    private String dirName;        //directory where stats files are placed
    List<GameResult> gameResults;

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public void setGameResults(List<GameResult> gameResults) {
        this.gameResults = gameResults;
    }
}
