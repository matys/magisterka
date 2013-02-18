package pl.edu.agh.mabics.ui.datamodel.beans;

import org.springframework.stereotype.Component;
import pl.edu.agh.mabics.agents.implementation.AgentType;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 24.12.12
 * Time: 15:18
 */
@Component
public class OneSideConfiguration {
    private Coordinates leftTopCornerCoordinates;
    private Coordinates rightDownCornerCoordinates;
    private Integer endLine;
    private Integer numberOfAgents;
    private List<AgentData> agents;
    private AgentType agentImplementation;

    public Coordinates getLeftTopCornerCoordinates() {
        return leftTopCornerCoordinates;
    }

    public void setLeftTopCornerCoordinates(Coordinates leftTopCornerCoordinates) {
        this.leftTopCornerCoordinates = leftTopCornerCoordinates;
    }

    public Coordinates getRightDownCornerCoordinates() {
        return rightDownCornerCoordinates;
    }

    public void setRightDownCornerCoordinates(Coordinates rightDownCornerCoordinates) {
        this.rightDownCornerCoordinates = rightDownCornerCoordinates;
    }

    public Integer getEndLine() {
        return endLine;
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public Integer getNumberOfAgents() {
        return numberOfAgents;
    }

    public void setNumberOfAgents(Integer numberOfAgents) {
        this.numberOfAgents = numberOfAgents;
    }

    public List<AgentData> getAgents() {
        return agents;
    }


    public void setAgentImplementation(AgentType type) {
        this.agentImplementation = type;
    }

    public AgentType getAgentImplementation() {
        return agentImplementation;
    }

    public void setAgents(List<AgentData> agents) {
        this.agents = agents;
    }

    public void copyDataTo(OneSideConfiguration anotherConfiguration) {
        anotherConfiguration.setLeftTopCornerCoordinates(getLeftTopCornerCoordinates());
        anotherConfiguration.setRightDownCornerCoordinates(getRightDownCornerCoordinates());
        anotherConfiguration.setNumberOfAgents(getNumberOfAgents());
        anotherConfiguration.setEndLine(getEndLine());
        anotherConfiguration.setAgents(getAgents());
        anotherConfiguration.setAgentImplementation(getAgentImplementation());
    }

}
