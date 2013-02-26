package pl.edu.agh.mabics.experiment.controllers;

import pl.edu.agh.mabics.experiment.datamodel.AgentStatistics;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 21.02.13
 * Time: 15:41
 */
public interface IGameRunner {

    void afterAgentStep(AgentStatistics statistics);
}
