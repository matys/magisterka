package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.agents.AbstractAgent;
import pl.edu.agh.mabics.agents.AgentFactory;
import pl.edu.agh.mabics.experiment.datamodel.GameResult;
import pl.edu.agh.mabics.experiment.util.ConfigurationFileBuilder;
import pl.edu.agh.mabics.ui.datamodel.beans.AgentData;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;
import pl.edu.agh.mabics.ui.datamodel.beans.OneSideConfiguration;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;
import pl.edu.agh.mabics.util.CommandLineHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 13.02.13
 * Time: 18:26
 */
@Controller
public class GameRunner {

    private static final String CONFIG_FILE_PATH = "..\\trunk\\src\\runner\\";
    private static final String CONFIG_FILE_NAME = "config3";

    private CommandLineHelper commandLineHelper;
    private AgentFactory agentFactory;
    private ConfigurationFileBuilder configurationFileBuilder;
    private Map<String, AbstractAgent> agents = new HashMap<String, AbstractAgent>();

    public GameResult runGame(int gameNumber, FormBean data) {
        //restartAgents(); perhaps not needed, perhaps it will restart some agent data that shouldn't be known between series
        startPlatform(data);
        return new GameResult();
    }

    private void startPlatform(FormBean data) {
        buildConfigurationFile(data);
        String visualisationCommand = getVisualizationEnabledCommand(data);
        String[] commands = {"cd " + CONFIG_FILE_PATH, "start python runner.py -c " + configurationFileBuilder.getFile() + " -v True"};
        commandLineHelper.runCommand(commands);
    }

    private String getVisualizationEnabledCommand(FormBean data) {
//        data.getExperimentConfiguration().
        return "";
    }

    private void buildConfigurationFile(FormBean data) {
        configurationFileBuilder.createNewConfigurationFile(CONFIG_FILE_PATH, CONFIG_FILE_NAME);
        configurationFileBuilder.writeIntersectionData(data.getIntersectionConfiguration());
        buildAgentData(data.getAgentsConfiguration().getLeftSideConfiguration());
        buildAgentData(data.getAgentsConfiguration().getDownSideConfiguration());
    }

    private void buildAgentData(OneSideConfiguration data) {
        for (AgentData agentData : data.getAgents()) {
            AbstractAgent agent = agents.get(agentData.getName());
            Coordinates endLine = new Coordinates(data.getEndLine(), 26);   //TODO fix
            configurationFileBuilder.writeAgentData(agentData, endLine, agent.getPort());
        }
    }

    public void initAgents(FormBean data) {
        initOneSide(data.getAgentsConfiguration().getDownSideConfiguration());
        initOneSide(data.getAgentsConfiguration().getLeftSideConfiguration());
    }

    private void initOneSide(OneSideConfiguration data) {
        for (AgentData agentData : data.getAgents()) {
            AbstractAgent agent = agentFactory.createAgent();
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
}
