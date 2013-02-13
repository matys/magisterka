package pl.edu.agh.mabics.experiment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.edu.agh.mabics.experiment.datamodel.GameResult;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;
import pl.edu.agh.mabics.util.CommandLineHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 13.02.13
 * Time: 18:26
 */
@Controller
public class GameRunner {

    CommandLineHelper commandLineHelper;

    public GameResult runGame(int gameNumber, FormBean data) {
        //restartAgents(); perhaps not needed, perhaps it will restart some agent data that shouldn't be known between series
        startPlatform(data);
        return new GameResult();
    }

    private void startPlatform(FormBean data) {
        String[] commands = {"cd ..\\trunk\\src\\runner\\", "start python runner.py -c config3 -v True"};
        commandLineHelper.runCommand(commands);
    }

    public void initAgents(FormBean data) {

    }

    @Autowired
    public void setCommandLineHelper(CommandLineHelper commandLineHelper) {
        this.commandLineHelper = commandLineHelper;
    }
}
