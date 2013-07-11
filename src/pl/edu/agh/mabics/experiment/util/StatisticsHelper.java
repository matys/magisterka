package pl.edu.agh.mabics.experiment.util;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.experiment.datamodel.GameResult;
import pl.edu.agh.mabics.experiment.datamodel.SimulationResult;
import pl.edu.agh.mabics.util.CommandLineHelper;
import pl.edu.agh.mabics.util.ReflectionHelper;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 28.02.13
 * Time: 12:34
 */

@Service
public class StatisticsHelper {
    public static final String TIME_OF_LAST_GETTER_NAME = "getTimeOfLast";
    public static final String AVERAGE_TIME_GETTER_NAME = "getAverageTime";
    public static final String COLLISIONS_GETTER_NAME = "getNumberOfCollisions";
    private static String TIME_OF_FIRST_GETTER_NAME = "getTimeOfFirst";
    private FileHelper fileHelper;
    private CommandLineHelper commandLineHelper;
    private ReflectionHelper reflectionHelper;
    private static String TIMES_OF_LAST_FILE_NAME = "timeLast.txt";
    private static String TIMES_OF_FIRST_FILE_NAME = "timeFirst.txt";
    private static String AVERAGE_TIMES_FILE_NAME = "timeAverage.txt";
    private static String COLLISIONS_NUMBERS_FILE_NAME = "collisionsQuantity.txt";
    private static String TIME_OF_LAST_STATISTIC_FILE_NAME = "timeLastStatistic.txt";
    private static String AVERAGE_TIMES_STATISTIC_FILE_NAME = "timeAverageStatistic.txt";
    private static String COLLISIONS_NUMBERS_STATISTIC_FILE_NAME = "collisionsQuantityStatistic.txt";
    private static String TIME_OF_FIRST_STATISTIC_FILE_NAME = "timeFirstStatistic.txt";
    private static String SIMULATION_DIR_NAME_PREFIX = "serie";


    public void saveSerieResults(int repetitionNumber, SimulationResult result) throws IOException {
        String dirName = initDirectory(repetitionNumber, result);
        BufferedWriter writerLast = fileHelper.createBufferedWriter(dirName + "\\" + TIMES_OF_LAST_FILE_NAME);
        BufferedWriter writerCollision = fileHelper.createBufferedWriter(dirName + "\\" + COLLISIONS_NUMBERS_FILE_NAME);
        BufferedWriter writerAverage = fileHelper.createBufferedWriter(dirName + "\\" + AVERAGE_TIMES_FILE_NAME);
        BufferedWriter writerFirst = fileHelper.createBufferedWriter(dirName + "\\" + TIMES_OF_FIRST_FILE_NAME);
        Integer i = 1;
        for (GameResult gameResult : result.getGameResults()) {
            writeLine(writerLast, i, gameResult.getTimeOfLast().toString());
            writeLine(writerAverage, i, gameResult.getAverageTime().toString());
            writeLine(writerCollision, i, gameResult.getNumberOfCollisions().toString());
            writeLine(writerFirst, i, gameResult.getTimeOfFirst().toString());
            i++;
        }
        fileHelper.closeBufferedWriters(new BufferedWriter[]{writerAverage, writerCollision, writerLast});
    }

    private void writeLine(BufferedWriter writer, Integer i, String value) throws IOException {
        writeLine(writer, i, new String[]{value});
    }

    private void writeLine(BufferedWriter writer, Integer i, String[] values) throws IOException {
        String value = "";
        for (String v : values) {
            value = value + " " + v;
        }
        writer.write(i.toString() + " " + value);
        writer.newLine();
    }

    private String initDirectory(int repetitionNumber, SimulationResult result) {
        String dirName = SIMULATION_DIR_NAME_PREFIX + repetitionNumber;
        result.setDirName(dirName);
        fileHelper.initDirectory(dirName);
        return dirName;
    }

    private void initDirectory(String outputDirName) {
        fileHelper.initDirectory(outputDirName);
    }

    @Autowired
    public void setFileHelper(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    @Autowired
    public void setCommandLineHelper(CommandLineHelper commandLineHelper) {
        this.commandLineHelper = commandLineHelper;
    }

    @Autowired
    public void setReflectionHelper(ReflectionHelper reflectionHelper) {
        this.reflectionHelper = reflectionHelper;
    }

    public void prepareStatisticFiles(Set<SimulationResult> simulationResults, int numberOfGames,
                                      String outputDirName) {
        initDirectory(outputDirName);
        prepareStatisticFile(simulationResults, numberOfGames, TIME_OF_LAST_GETTER_NAME,
                outputDirName + "\\" + TIME_OF_LAST_STATISTIC_FILE_NAME);
        prepareStatisticFile(simulationResults, numberOfGames, COLLISIONS_GETTER_NAME,
                outputDirName + "\\" + COLLISIONS_NUMBERS_STATISTIC_FILE_NAME);
        prepareStatisticFile(simulationResults, numberOfGames, AVERAGE_TIME_GETTER_NAME,
                outputDirName + "\\" + AVERAGE_TIMES_STATISTIC_FILE_NAME);
        prepareStatisticFile(simulationResults, numberOfGames, TIME_OF_FIRST_GETTER_NAME,
                outputDirName + "\\" + TIME_OF_FIRST_STATISTIC_FILE_NAME);

    }


    public void prepareStatisticFile(Set<SimulationResult> simulationResults, int numberOfGames, String valueGetterName,
                                     String fileName) {
        BufferedWriter fileWriter = fileHelper.createBufferedWriter(fileName);
        for (int i = 0; i < numberOfGames; i++) {
            DescriptiveStatistics stats = new DescriptiveStatistics();
            for (SimulationResult simulationResult : simulationResults) {
                stats.addValue(reflectionHelper.getValue(simulationResult.getGameResults().get(i), valueGetterName));
            }
            Double mean = stats.getMean();
            Double std = stats.getStandardDeviation();
            try {
                writeLine(fileWriter, i + 1,
                        new String[]{mean.toString(), new Double(mean - std).toString(), new Double(mean + std)
                                .toString()});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileHelper.closeBufferedWriter(fileWriter);
    }


    public String[] getStatisticFilesNames() {
        return new String[]{AVERAGE_TIMES_STATISTIC_FILE_NAME, TIME_OF_LAST_STATISTIC_FILE_NAME, COLLISIONS_NUMBERS_STATISTIC_FILE_NAME, TIME_OF_FIRST_STATISTIC_FILE_NAME};
    }
}

