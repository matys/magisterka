package pl.edu.agh.mabics.experiment.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.util.CommandLineHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 03.03.13
 * Time: 21:15
 */
@Service
public class GraphsHelper {

    private static final String OUTPUT_FORMAT = "png";
    public static final String HISTOGRAM_FILE_SUFFIX = "Histogram";
    private CommandLineHelper commandLineHelper;
    private FileHelper fileHelper;

    public List<String> createGraphs(List<String> statisticFiles, String outputDirName, Integer samplingFrequency,
                                     Integer boxWidth) {
        List<String> outputFilesNames = new ArrayList<String>();
        for (String statisticFile : statisticFiles) {
            createNormalGraph(outputDirName, samplingFrequency, outputFilesNames, statisticFile);
            createHistogram(outputDirName, boxWidth, outputFilesNames, statisticFile);
        }
        return outputFilesNames;
    }

    private void createHistogram(String histogramOutputDirName, Integer boxWidth, List<String> outputFilesNames,
                                 String statisticFile) {
        createHistogram(histogramOutputDirName, boxWidth, outputFilesNames, statisticFile, histogramOutputDirName);
    }

    public void createHistogram(String histogramOutputDirName, Integer boxWidth, List<String> outputFilesNames,
                                String statisticFile, String directory) {
        statisticFile = createHistogramFile(statisticFile, boxWidth, directory, histogramOutputDirName);
        String outputFileName = fileHelper.removeExtension(statisticFile);
        Map<String, String> parameters = createCommonParameters(histogramOutputDirName, statisticFile, outputFileName,
                histogramOutputDirName);
        String command = "gnuplot -e " + toGnuplotCommand(parameters) + " plot_histograms.p";
        commandLineHelper.runCommand(new String[]{command}, false);
        outputFilesNames.add(outputFileName + "." + OUTPUT_FORMAT);

    }


    private String createHistogramFile(String statisticFile, Integer boxWidth, String directory,
                                       String histogramOutputDir) {
        BufferedReader reader = fileHelper.createBufferedReader(directory + "\\" + statisticFile);
        String histogramFileName = buildHistogramFileName(statisticFile);
        BufferedWriter writer = fileHelper.createBufferedWriter(histogramOutputDir + "\\" + histogramFileName);
        String line;
        int lineCounter = 0;
        double boxSum = 0.0d;
        int nbrOfBoxes = 0;
        try {
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty()) {
                    System.out.println(")))))((((" + line + "        " + line.split("\\s+")[1]);
                    boxSum += Double.parseDouble(line.split("\\s+")[1]);
                    if (lineCounter == boxWidth) {
                        nbrOfBoxes += 1;
                        writer.write(
                                (lineCounter * nbrOfBoxes - boxWidth) + "-" + lineCounter * nbrOfBoxes + " " + boxSum);
                        writer.newLine();
                        lineCounter = 0;
                        boxSum = 0.0d;
                    }
                    lineCounter++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileHelper.closeBufferedWriter(writer);
        return histogramFileName;
    }

    private String buildHistogramFileName(String statisticFile) {
        return fileHelper.removeExtension(statisticFile) +
                HISTOGRAM_FILE_SUFFIX + ".txt";
    }

    private void createNormalGraph(String outputDirName, Integer samplingFrequency, List<String> outputFilesNames,
                                   String statisticFile) {
        String outputFileName = fileHelper.removeExtension(statisticFile);
        Map<String, String> parameters = createCommonParameters(outputDirName, statisticFile, outputFileName,
                outputDirName);
        parameters.put("frequency", samplingFrequency.toString());
        String command = "gnuplot -e " + toGnuplotCommand(parameters) + " plot_statistics.p";
        commandLineHelper.runCommand(new String[]{command}, false);
        outputFilesNames.add(outputFileName + "." + OUTPUT_FORMAT);
    }

    private Map<String, String> createCommonParameters(String outputDirName, String statisticFile,
                                                       String outputFileName, String inputDirName) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("outfolder", outputDirName);
        parameters.put("infolder", inputDirName);
        parameters.put("fileName", statisticFile);
        parameters.put("format", OUTPUT_FORMAT);
        parameters.put("title", outputFileName);
        return parameters;
    }

    private String toGnuplotCommand(Map<String, String> parameters) {
        String command = "\"";
        for (String key : parameters.keySet()) {
            command = command + key + "='" + parameters.get(key) + "'; ";
        }
        command = command + "\"";
        return command;

//        "folder='" + outputDirName + "'; " + "fileName='" + statisticFile + "'; title='" + fileHelper.removeExtension(statisticFile) + "'"
    }


    @Autowired
    public void setCommandLineHelper(CommandLineHelper commandLineHelper) {
        this.commandLineHelper = commandLineHelper;
    }

    @Autowired
    public void setFileHelper(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        GraphsHelper commandLineHelper = (GraphsHelper) context.getBean("graphsHelper");

        List<String> files = new ArrayList<String>();
        files.add("timeAverageStatistic.txt");
        files.add("timeLastStatistic.txt");
        files.add("collisionsQuantityStatistic.txt");
        files.add("timeFirstStatistic.txt");
        files.add("agentLeft1\\timeStatistic.txt");
        files.add("agentLeft1\\collisionsQuantityStatistic.txt");
        files.add("agentLeft0\\timeStatistic.txt");
        files.add("agentLeft0\\collisionsQuantityStatistic.txt");
        files.add("agentLeft2\\timeStatistic.txt");
        files.add("agentLeft2\\collisionsQuantityStatistic.txt");
        String output = "zosia\\alpha0.45lambda0.9epsilon0.2gamma0.9collisionReward-100.0stepReward-20.0gettingToTargetReward100.0";
        commandLineHelper.createGraphs(files, output, 4, 150);
    }
}

