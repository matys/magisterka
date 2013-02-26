package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.agents.AbstractAgent;
import pl.edu.agh.mabics.agents.AgentFactory;
import pl.edu.agh.mabics.experiment.datamodel.AgentStatistics;
import pl.edu.agh.mabics.experiment.datamodel.GameResult;
import pl.edu.agh.mabics.experiment.util.AgentDataHelper;
import pl.edu.agh.mabics.experiment.util.ConfigurationFileBuilder;
import pl.edu.agh.mabics.ui.datamodel.beans.AgentData;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;
import pl.edu.agh.mabics.ui.datamodel.beans.OneSideConfiguration;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;
import pl.edu.agh.mabics.util.AgentSite;
import pl.edu.agh.mabics.util.CommandLineHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 13.02.13
 * Time: 18:26
 */
@Controller
public class GameRunner implements IGameRunner {

    private static final String CONFIG_FILE_PATH = "..\\trunk\\src\\runner\\";
    private static final String CONFIG_FILE_NAME = "config3";

    private CommandLineHelper commandLineHelper;
    private AgentFactory agentFactory;
    private AgentDataHelper agentDataHelper;
    private ConfigurationFileBuilder configurationFileBuilder;
    private CollisionController collisionController;
    private EndGameController endGameController;
    private Map<String, AbstractAgent> agents = new HashMap<String, AbstractAgent>();
    private Process platformThread;
    private boolean finished;

    public GameResult runGame(int gameNumber, FormBean data) {
        //restartAgents(); perhaps not needed, perhaps it will restart some agent data that shouldn't be known between series
        initCollisionController();
        initEndGameController(data);
        startPlatform(data);
        finished = false;
        while (!finished) {
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return new GameResult();
    }

    private void initEndGameController(FormBean data) {
        HashMap<String, List<Coordinates>> agentsDestination = new HashMap<String, List<Coordinates>>();
        addOneSideAgents(data.getAgentsConfiguration().getDownSideConfiguration(), agentsDestination, AgentSite.DOWN);
        addOneSideAgents(data.getAgentsConfiguration().getLeftSideConfiguration(), agentsDestination, AgentSite.LEFT);
        endGameController.init(agentsDestination);
    }

    private void addOneSideAgents(OneSideConfiguration data, HashMap<String, List<Coordinates>> agentsDestination, AgentSite site) {
        List<Coordinates> destinationCoordinates = new ArrayList<Coordinates>();
        switch (site) {
            case DOWN:
                destinationCoordinates.addAll(agentDataHelper.generateDownEndlinePoints(data));
                break;
            case LEFT:
                destinationCoordinates.addAll(agentDataHelper.generateLeftEndlinePoints(data));
                break;
        }
        for (AgentData agent : data.getAgents()) {
            agentsDestination.put(agent.getName(), destinationCoordinates);
        }
    }

    private void initCollisionController() {
        collisionController.init(agents.size());
        if (!collisionController.isAlive()) {
            collisionController.start();
        }
    }

    private void startPlatform(FormBean data) {
        buildConfigurationFile(data);
        String visualisationCommand = getVisualizationEnabledCommand(data);
        String[] commands = {"cd " + CONFIG_FILE_PATH, "start python runner.py -c " + configurationFileBuilder.getFile() + " -v True"};
        platformThread = commandLineHelper.runCommand(commands, false);
    }

    //TODO implement
    private String getVisualizationEnabledCommand(FormBean data) {
//        data.getExperimentConfiguration().
        return "";
    }

    private void buildConfigurationFile(FormBean data) {
        configurationFileBuilder.createNewConfigurationFile(CONFIG_FILE_PATH, CONFIG_FILE_NAME);
        configurationFileBuilder.writeIntersectionData(data.getIntersectionConfiguration());
        buildAgentData(data.getAgentsConfiguration().getLeftSideConfiguration(), AgentSite.LEFT);
        buildAgentData(data.getAgentsConfiguration().getDownSideConfiguration(), AgentSite.DOWN);
    }

    private void buildAgentData(OneSideConfiguration data, AgentSite site) {
        for (AgentData agentData : data.getAgents()) {
            AbstractAgent agent = agents.get(agentData.getName());
            List<Coordinates> endLine = new ArrayList<Coordinates>();
            switch (site) {
                case DOWN:
                    endLine.addAll(agentDataHelper.generateDownEndlinePoints(data));
                    break;
                case LEFT:
                    endLine.addAll(agentDataHelper.generateLeftEndlinePoints(data));
                    break;
            }
            configurationFileBuilder.writeAgentData(agentData, endLine, agent.getPort());
        }
    }


    public void initAgents(FormBean data) {
        initOneSide(data.getAgentsConfiguration().getDownSideConfiguration());
        initOneSide(data.getAgentsConfiguration().getLeftSideConfiguration());
    }

    private void initOneSide(OneSideConfiguration data) {
        for (AgentData agentData : data.getAgents()) {
            AbstractAgent agent = agentFactory.createAgent(agentData.getName(), data.getAgentImplementation());
            agents.put(agentData.getName(), agent);
        }
    }

    @Autowired
    public void setCommandLineHelper(CommandLineHelper commandLineHelper) {
        this.commandLineHelper = commandLineHelper;
    }

    @Autowired
    public void setAgentFactory(AgentFactory agentFactory) {
        this.agentFactory = agentFactory;
    }

    @Autowired
    public void setConfigurationFileBuilder(ConfigurationFileBuilder configurationFileBuilder) {
        this.configurationFileBuilder = configurationFileBuilder;
    }

    @Autowired
    public void setAgentDataHelper(AgentDataHelper agentDataHelper) {
        this.agentDataHelper = agentDataHelper;
    }

    @Autowired
    public void setCollisionController(CollisionController collisionController) {
        this.collisionController = collisionController;
    }

    @Autowired
    public void setEndGameController(EndGameController endGameController) {
        this.endGameController = endGameController;
    }

    @Override
    public void afterAgentStep(AgentStatistics statistics) {
        if (endGameController.isAllAgentFinished()) {
            System.out.println("FINISHED!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            for (AbstractAgent agent : agents.values()) {
                try {
                    agent.stopAgent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                createStatistics();
            }
        }
    }

    private void createStatistics() {
        finished = true;
    }

    public void restartAgents() {
        for (AbstractAgent agent : agents.values()) {
            agent.startAgent(agent.getPort(), agent.getId());
        }
    }
}
