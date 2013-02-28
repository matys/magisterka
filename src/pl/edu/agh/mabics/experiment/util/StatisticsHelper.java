package pl.edu.agh.mabics.experiment.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.experiment.datamodel.GameResult;
import pl.edu.agh.mabics.experiment.datamodel.SimulationResult;
import pl.edu.agh.mabics.util.CommandLineHelper;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 28.02.13
 * Time: 12:34
 */

@Service
public class StatisticsHelper {
    private FileHelper fileHelper;
    private CommandLineHelper commandLineHelper;
    private static String TIME_OF_LAST_STATISTIC_FILE_NAME = "timeLast.txt";
    private static String AVERAGE_TIME_STATISTIC_FILE_NAME = "timeAverage.txt";
    private static String COLLISIONS_NUMBER_STATISTIC_FILE_NAME = "collisionsQuantity.txt";
    private static String SIMULATION_DIR_NAME_PREFIX = "simulation";

    public void saveSimulationResults(int repetitionNumber, SimulationResult result) throws IOException {
        String dirName = initDirectory(repetitionNumber, result);
        BufferedWriter writerLast = fileHelper.createBufferedReader(dirName + "\\" + TIME_OF_LAST_STATISTIC_FILE_NAME);
        BufferedWriter writerCollision = fileHelper.createBufferedReader(dirName + "\\" + COLLISIONS_NUMBER_STATISTIC_FILE_NAME);
        BufferedWriter writerAverage = fileHelper.createBufferedReader(dirName + "\\" + AVERAGE_TIME_STATISTIC_FILE_NAME);
        Integer i = 1;
        for (GameResult gameResult : result.getGameResults()) {
            writeLine(writerLast, i, gameResult.getTimeOfLast().toString());
            writeLine(writerAverage, i, gameResult.getAverageTime().toString());
            writeLine(writerCollision, i, gameResult.getNumberOfCollisions().toString());
            i++;
        }
        fileHelper.closeBufferedWriters(new BufferedWriter[]{writerAverage, writerCollision, writerLast});
    }

    private void writeLine(BufferedWriter writer, Integer i, String value) throws IOException {
        writer.write(i.toString() + " " + value);
        writer.newLine();
    }

    private String initDirectory(int repetitionNumber, SimulationResult result) {
        String dirName = SIMULATION_DIR_NAME_PREFIX + repetitionNumber;
        result.setDirName(dirName);
        commandLineHelper.runCommand(new String[]{"rmdir /q /s " + dirName}, true);
        commandLineHelper.runCommand(new String[]{"mkdir " + dirName}, true);
        return dirName;
    }

    @Autowired
    public void setFileHelper(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    @Autowired
    public void setCommandLineHelper(CommandLineHelper commandLineHelper) {
        this.commandLineHelper = commandLineHelper;
    }
}

