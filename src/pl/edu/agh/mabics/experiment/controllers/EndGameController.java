package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.ui.datamodel.beans.AgentData;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;
import pl.edu.agh.mabics.util.AgentSite;

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

    public boolean isAgentInEndPoint(String agentId, Coordinates position) {
        AgentState agentState = agentsStates.get(agentId);
        AgentSite agentSite = agentState.agentSite;
        int progress = 0;
        switch (agentSite) {
            case LEFT:
                progress = position.getX();
                break;
            case DOWN:
                progress = position.getY();
                break;
        }
        if (progress >= agentState.agentEndLine) {
            agentState.inEndPoint = true;
            return true;
        }
        return false;
    }

    public void init(List<AgentData> downAgents, int topEndLine, List<AgentData> leftAgents, int rightEndLine) {
        for (AgentData agent : downAgents) {
            agentsStates.put(agent.getName(), new AgentState(AgentSite.DOWN, topEndLine));
        }
        for (AgentData agent : leftAgents) {
            agentsStates.put(agent.getName(), new AgentState(AgentSite.LEFT, rightEndLine));
        }
    }

    public boolean isAllAgentFinished() {
        for (AgentState state : agentsStates.values()) {
            if (!state.inEndPoint) return false;
        }
        return true;
    }

    private class AgentState {
        boolean inEndPoint = false;
        public AgentSite agentSite;
        private int agentEndLine;

        public AgentState(AgentSite agentSite, int agentEndLine) {
            this.agentSite = agentSite;
            this.agentEndLine = agentEndLine;
        }
    }

}
