package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.experiment.datamodel.GameResult;
import pl.edu.agh.mabics.experiment.datamodel.SimulationResult;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 13.02.13
 * Time: 18:20
 */
@Controller
public class SimulationRunner {

    private GameRunner gameRunner;

    public SimulationResult startSimulation(int repetitionNumber, FormBean data) {
        SimulationResult result = new SimulationResult();
        Integer gamesNumber = data.getExperimentConfiguration().getNumberOfGames();
        List<GameResult> gamesResults = new ArrayList<GameResult>(gamesNumber);
        //init agents (knowledge doesn't change between games)
        gameRunner.initAgents(data);
        for (int gameNumber = 0; gameNumber < gamesNumber; gameNumber++) {
            gamesResults.add(gameRunner.runGame(gameNumber, data));
            if (gameNumber < gamesNumber - 1)
                gameRunner.restartAgents();
        }
        result.setGameResults(gamesResults);
        return result;
    }

    @Autowired
    public void setGameRunner(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
    }
}
