package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.experiment.datamodel.SimulationResult;
import pl.edu.agh.mabics.experiment.util.GraphsHelper;
import pl.edu.agh.mabics.experiment.util.StatisticsHelper;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 16.12.12
 * Time: 15:13
 * <p/>
 * Controls whole experiment, should inject to agents appropriate implementations of loggers, restarts series, create output graphs, etc.
 */
@Controller
public class ExperimentRunner {

    private SimulationRunner simulationRunner;
    private GraphsHelper graphsHelper;
    private StatisticsHelper statisticsHelper;

    /**
     * @param data
     * @return names of files with plotted results of experiment
     */
    public List<String> startExperiment(FormBean data) {
        int repetitionsQuantity = data.getExperimentConfiguration().getNumberOfSeries();
        Set<SimulationResult> simulationResults = new HashSet<SimulationResult>(repetitionsQuantity);
        for (int repetitionNumber = 0; repetitionNumber < repetitionsQuantity; repetitionNumber++) {
            simulationResults.add(simulationRunner.startSimulation(repetitionNumber, data));
        }
        statisticsHelper.prepareStatisticFiles(simulationResults, data.getExperimentConfiguration().getNumberOfGames(), data.getExperimentConfiguration().getOutputDirName());
        return graphsHelper.createGraphs(statisticsHelper.getStatisticFilesNames(), data.getExperimentConfiguration().getOutputDirName());
    }

    @Autowired
    public void setSimulationRunner(SimulationRunner simulationRunner) {
        this.simulationRunner = simulationRunner;
    }

    @Autowired
    public void setGraphsHelper(GraphsHelper graphsHelper) {
        this.graphsHelper = graphsHelper;
    }

    @Autowired
    public void setStatisticsHelper(StatisticsHelper statisticsHelper) {
        this.statisticsHelper = statisticsHelper;
    }
}
