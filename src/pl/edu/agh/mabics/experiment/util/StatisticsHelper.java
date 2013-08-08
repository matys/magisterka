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
import java.util.*;

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
    private static String TIME_FILE_NAME = "time.txt";
    private static String COLLISIONS_NUMBERS_FILE_NAME = "collisionsQuantity.txt";
    private static String TIME_OF_LAST_STATISTIC_FILE_NAME = "timeLastStatistic.txt";
    private static String AVERAGE_TIMES_STATISTIC_FILE_NAME = "timeAverageStatistic.txt";
    private static String COLLISIONS_NUMBERS_STATISTIC_FILE_NAME = "collisionsQuantityStatistic.txt";
    private static String TIME_OF_FIRST_STATISTIC_FILE_NAME = "timeFirstStatistic.txt";
    private static String SIMULATION_DIR_NAME_PREFIX = "serie";
    private static String TIMES_STATISTIC_FILE_NAME = "timeStatistic.txt";
    private static String TIMES_PER_AGENT_GETTER_NAME = "getTimePerAgent";
    private static String COLLISIONS_PER_AGENT_GETTER_NAME = "getCollisionsPerAgent";


    public void saveSerieResults(int repetitionNumber, SimulationResult result) throws IOException {
        String dirName = initDirectories(repetitionNumber, result);
        BufferedWriter writerLast = fileHelper.createBufferedWriter(dirName + "\\" + TIMES_OF_LAST_FILE_NAME);
        BufferedWriter writerCollision = fileHelper.createBufferedWriter(dirName + "\\" + COLLISIONS_NUMBERS_FILE_NAME);
        BufferedWriter writerAverage = fileHelper.createBufferedWriter(dirName + "\\" + AVERAGE_TIMES_FILE_NAME);
        BufferedWriter writerFirst = fileHelper.createBufferedWriter(dirName + "\\" + TIMES_OF_FIRST_FILE_NAME);
        Map<String, BufferedWriter> collisionWriterPerAgent = createBufferedWriterPerAgent(result, dirName,
                COLLISIONS_NUMBERS_FILE_NAME);
        Map<String, BufferedWriter> timeWriterPerAgent = createBufferedWriterPerAgent(result, dirName, TIME_FILE_NAME);
        Integer i = 1;
        for (GameResult gameResult : result.getGameResults()) {
            writeLine(writerLast, i, gameResult.getTimeOfLast().toString());
            writeLine(writerAverage, i, gameResult.getAverageTime().toString());
            writeLine(writerCollision, i, gameResult.getNumberOfCollisions().toString());
            writeLine(writerFirst, i, gameResult.getTimeOfFirst().toString());
            for (String agentId : gameResult.getCollisionsPerAgent().keySet()) {
                writeLine(collisionWriterPerAgent.get(agentId), i,
                        gameResult.getCollisionsPerAgent().get(agentId).toString());
                writeLine(timeWriterPerAgent.get(agentId), i, gameResult.getTimePerAgent().get(agentId).toString());
            }
            i++;
        }
        fileHelper.closeBufferedWriters(new BufferedWriter[]{writerAverage, writerCollision, writerLast});
        for (BufferedWriter writer : collisionWriterPerAgent.values()) {
            fileHelper.closeBufferedWriter(writer);
        }
        for (BufferedWriter writer : timeWriterPerAgent.values()) {
            fileHelper.closeBufferedWriter(writer);
        }

    }

    private Map<String, BufferedWriter> createBufferedWriterPerAgent(SimulationResult result, String dirName,
                                                                     String fileName) {
        Map<String, BufferedWriter> writersPerAgent = new HashMap<String, BufferedWriter>();
        Set<String> agentsIds = result.getGameResults().get(0).getTimePerAgent().keySet();
        for (String agentId : agentsIds) {
            String agentDirName = buildDirNamePerAgent(dirName, agentId);
            writersPerAgent.put(agentId, fileHelper.createBufferedWriter(agentDirName + "\\" +
                    fileName));
        }
        return writersPerAgent;
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

    private String initDirectories(int repetitionNumber, SimulationResult result) {
        String statisticBaseDirectory = SIMULATION_DIR_NAME_PREFIX + repetitionNumber;
        result.setDirName(statisticBaseDirectory);
        fileHelper.initDirectory(statisticBaseDirectory);
        initAgentStatisticsDirectory(result, statisticBaseDirectory);
        return statisticBaseDirectory;
    }

    private void initAgentStatisticsDirectory(SimulationResult result, String statisticBaseDirectory) {
        Set<String> agentsIds = result.getGameResults().get(0).getTimePerAgent().keySet();
        for (String agentId : agentsIds) {
            fileHelper.initDirectory(buildDirNamePerAgent(statisticBaseDirectory, agentId));
        }
    }

    private String buildDirNamePerAgent(String statisticBaseDirectory, String agentId) {
        return statisticBaseDirectory + "\\" + agentId;
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
        initAgentStatisticsDirectory((SimulationResult) simulationResults.toArray()[0], outputDirName);
        prepareAgentStatisticFile(simulationResults, numberOfGames, TIMES_PER_AGENT_GETTER_NAME,
                TIMES_STATISTIC_FILE_NAME, outputDirName);
        prepareAgentStatisticFile(simulationResults, numberOfGames, COLLISIONS_PER_AGENT_GETTER_NAME,
                COLLISIONS_NUMBERS_STATISTIC_FILE_NAME, outputDirName);

    }


    public void prepareStatisticFile(Set<SimulationResult> simulationResults, int numberOfGames, String valueGetterName,
                                     String fileName) {
        BufferedWriter fileWriter = fileHelper.createBufferedWriter(fileName);
        for (int i = 0; i < numberOfGames; i++) {
            DescriptiveStatistics stats = new DescriptiveStatistics();
            for (SimulationResult simulationResult : simulationResults) {
                stats.addValue(
                        (Double) reflectionHelper.getValue(simulationResult.getGameResults().get(i), valueGetterName));
            }
            calculateAndWriteStatsToFile(fileWriter, i, stats);
        }
        fileHelper.closeBufferedWriter(fileWriter);
    }

    public void prepareAgentStatisticFile(Set<SimulationResult> simulationResults, int numberOfGames,
                                          String valueGetterName, String fileName, String agentDirName) {

        for (String agentId : ((SimulationResult) simulationResults.toArray()[0]).getGameResults().get(0)
                .getCollisionsPerAgent().keySet()) {
            BufferedWriter fileWriter = fileHelper.createBufferedWriter(agentDirName + "\\" + agentId + "\\" +
                    fileName);
            for (int i = 0; i < numberOfGames; i++) {
                DescriptiveStatistics stats = new DescriptiveStatistics();
                for (SimulationResult simulationResult : simulationResults) {
                    Map<String, Integer> valuesPerAgent = (Map<String, Integer>) reflectionHelper
                            .getValue(simulationResult.getGameResults().get(i), valueGetterName);
                    stats.addValue(valuesPerAgent.get(agentId));
                }
                calculateAndWriteStatsToFile(fileWriter, i, stats);
            }
            fileHelper.closeBufferedWriter(fileWriter);
        }
    }

    private void calculateAndWriteStatsToFile(BufferedWriter fileWriter, int i, DescriptiveStatistics stats) {
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

    public List<String> getStatisticFilesNames(Set<SimulationResult> simulationResults) {
        List<String> fileNames = new ArrayList<String>();
        fileNames.addAll(Arrays
                .asList(new String[]{AVERAGE_TIMES_STATISTIC_FILE_NAME, TIME_OF_LAST_STATISTIC_FILE_NAME, COLLISIONS_NUMBERS_STATISTIC_FILE_NAME, TIME_OF_FIRST_STATISTIC_FILE_NAME}));
        for (String agentId : ((SimulationResult) simulationResults.toArray()[0]).getGameResults().get(0)
                .getCollisionsPerAgent().keySet()) {
            fileNames.add(agentId + "\\" + TIMES_STATISTIC_FILE_NAME);
            fileNames.add(agentId + "\\" + COLLISIONS_NUMBERS_STATISTIC_FILE_NAME);

        }
        return fileNames;
    }
}

