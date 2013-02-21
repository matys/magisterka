package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 21.02.13
 * Time: 13:05
 */

@Service
public class EndGameController {


    private Map<String, AgentState> agentsStates = new HashMap<String, AgentState>();

    public boolean isAgentInEndPoint(String agentId, Coordinates position, List<Coordinates> destination) {
        for (Coordinates destinationPoint : destination) {
            if (position.equals(destinationPoint)) {
                agentsStates.get(agentId).inEndPoint = true;
                return true;
            }
        }
        return false;
    }

    public void init(HashMap<String, List<Coordinates>> agents) {
        for (String agentId : agents.keySet()) {
            List<Coordinates> destination = agents.get(agentId);
            AgentState agentState = new AgentState(destination);
            agentsStates.put(agentId, agentState);
        }
    }

    public boolean isAllAgentFinished() {
        for (AgentState state : agentsStates.values()) {
            if (!state.inEndPoint)
                return false;
        }
        return true;
    }

    private class AgentState {
        List<Coordinates> destination;
        Coordinates currentPosition;
        boolean inEndPoint = false;

        public AgentState(List<Coordinates> destination) {
            this.destination = destination;
        }
    }

}
