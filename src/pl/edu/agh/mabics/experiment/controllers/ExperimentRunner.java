package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.experiment.datamodel.SimulationResult;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;

import java.util.HashSet;
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

    public void startExperiment(FormBean data) {
        int repetitionsQuantity = data.getExperimentConfiguration().getNumberOfSeries();
        Set<SimulationResult> simulationResults = new HashSet<SimulationResult>(repetitionsQuantity);
        for (int repetitionNumber = 0; repetitionNumber < repetitionsQuantity; repetitionNumber++) {
            simulationResults.add(simulationRunner.startSimulation(repetitionNumber, data));
        }
    }

    @Autowired
    public void setSimulationRunner(SimulationRunner simulationRunner) {
        this.simulationRunner = simulationRunner;
    }
}
