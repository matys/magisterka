package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.experiment.datamodel.SimulationResult;
import pl.edu.agh.mabics.experiment.util.*;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;
import pl.edu.agh.mabics.ui.datamodel.beans.IntersectionConfiguration;

import java.io.BufferedWriter;
import java.io.IOException;
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

    private static final String PHYSIC_FILE_PATH = "..\\trunk\\src\\runner\\";
    private SimulationRunner simulationRunner;
    private GraphsHelper graphsHelper;
    private StatisticsHelper statisticsHelper;
    private FileHelper fileHelper;

    /**
     * @param data
     * @return names of files with plotted results of experiment
     */
    public List<String> startExperiment(FormBean data) {
        initPhysicFile(data.getIntersectionConfiguration());
        int repetitionsQuantity = data.getExperimentConfiguration().getNumberOfSeries();
        Set<SimulationResult> simulationResults = new HashSet<SimulationResult>(repetitionsQuantity);
        for (int repetitionNumber = 0; repetitionNumber < repetitionsQuantity; repetitionNumber++) {
            simulationResults.add(simulationRunner.startSimulation(repetitionNumber, data));
            waitForExternalPlatform();
        }
        statisticsHelper.prepareStatisticFiles(simulationResults, data.getExperimentConfiguration().getNumberOfGames(),
                data.getExperimentConfiguration().getOutputDirName());
        return graphsHelper.createGraphs(statisticsHelper.getStatisticFilesNames(),
                data.getExperimentConfiguration().getOutputDirName(),
                data.getExperimentConfiguration().getSamplingFrequency());
    }

    private void initPhysicFile(IntersectionConfiguration intersectionConfiguration) {
        PhysicType physicType = intersectionConfiguration.getPhysic();
        Physic physic = null;
        switch (physicType) {
            case StraightOnly:
                physic = new StraightOnlyPhysic(intersectionConfiguration.getMaxSpeed(),
                        intersectionConfiguration.getMaxSpeedChange());
                break;
        }
        BufferedWriter writer = fileHelper.createBufferedWriter(PHYSIC_FILE_PATH + physicType.getPhysicFile());
        try {
            physic.writePhysicToFile(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileHelper.closeBufferedWriter(writer);
    }

    private void waitForExternalPlatform() {
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    @Autowired
    public void setFileHelper(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }
}
