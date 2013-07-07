package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.experiment.datamodel.GameResult;
import pl.edu.agh.mabics.experiment.datamodel.SimulationResult;
import pl.edu.agh.mabics.experiment.util.StatisticsHelper;
import pl.edu.agh.mabics.ui.datamodel.beans.AgentData;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;
import pl.edu.agh.mabics.ui.datamodel.beans.OneSideConfiguration;
import pl.edu.agh.mabics.ui.listeners.helpers.AgentListenersHelper;

import java.io.IOException;
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
    private StatisticsHelper statisticsHelper;

    public SimulationResult startSimulation(int repetitionNumber, FormBean data) {
        SimulationResult result = new SimulationResult();
        Integer gamesNumber = data.getExperimentConfiguration().getNumberOfGames();
        List<GameResult> gamesResults = new ArrayList<GameResult>(gamesNumber);
        //init agents (knowledge doesn't change between games)
        gameRunner.initAgents(data);
        for (int gameNumber = 0; gameNumber < gamesNumber; gameNumber++) {
            GameResult gameResult = gameRunner.runGame(gameNumber, data);
            if (data.isGenerateForEveryGame()) {
                regenerateAgentsLocation(data);
            }
            if (gameResult != null) {
                gamesResults.add(gameResult);
                if (gameNumber < gamesNumber - 1) {
                    waitForExternalPlatform();
                    gameRunner.restartAgents();
                }
            } else {      //restart the same game
                gameNumber--;
                gameRunner.restartAgents();
            }
        }
        result.setGameResults(gamesResults);
        try {
            statisticsHelper.saveSerieResults(repetitionNumber, result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void regenerateAgentsLocation(FormBean data) {
        regenerateOneSideAgentsLocation(data.getAgentsConfiguration().getDownSideConfiguration());
        regenerateOneSideAgentsLocation(data.getAgentsConfiguration().getLeftSideConfiguration());
    }

    private void regenerateOneSideAgentsLocation(OneSideConfiguration sideConfiguration) {
        List<AgentData> oldAgents = sideConfiguration.getAgents();
        List<AgentData> newAgents = new ArrayList<AgentData>();
        for (AgentData oldAgent : oldAgents) {
            AgentData newAgent = AgentListenersHelper
                    .createUniqueRandomAgentData(newAgents, sideConfiguration.getLeftTopCornerCoordinates(),
                            sideConfiguration.getRightDownCornerCoordinates());
            newAgent.setName(oldAgent.getName());
            newAgents.add(newAgent);
        }
        sideConfiguration.setAgents(newAgents);
    }

    private void waitForExternalPlatform() {
        try {
            Thread.currentThread().sleep(6000); //wait for external platform to stop
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setGameRunner(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
    }

    @Autowired
    public void setStatisticsHelper(StatisticsHelper statisticsHelper) {
        this.statisticsHelper = statisticsHelper;
    }
}
