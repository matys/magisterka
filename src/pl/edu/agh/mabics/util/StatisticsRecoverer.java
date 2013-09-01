package pl.edu.agh.mabics.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.experiment.util.FileHelper;
import pl.edu.agh.mabics.experiment.util.GraphsHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 29.08.13
 * Time: 21:19
 */
@Service
public class StatisticsRecoverer {

    private FileHelper fileHelper;
    private GraphsHelper graphsHelper;
    private CommandLineHelper commandLineHelper;

    public void recover(String directory, Integer boxWidth, String outputDirectory, String[] filesToRecalculate) {
        for (String fileToRecalculate : filesToRecalculate) {
//            List<Double> resultsPerGame = getResultsPerGame(directory, fileToRecalculate);
//            List<Double> boxes = splitToBoxes(resultsPerGame, boxWidth);
//            String outputFileName = prepareOutputFile(filesToRecalculate);
//            createHistogramFile(outputDirectory, outputFileName, boxes);
//            drawHistogram(outputDirectory, outputFileName);
            List<String> outputFilesNames = new ArrayList<String>();
            graphsHelper.createHistogram(outputDirectory, boxWidth, outputFilesNames, fileToRecalculate, directory);
        }

    }


    @Autowired
    public void setFileHelper(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }

    @Autowired
    public void setGraphsHelper(GraphsHelper graphsHelper) {
        this.graphsHelper = graphsHelper;
    }

    @Autowired
    public void setCommandLineHelper(CommandLineHelper commandLineHelper) {
        this.commandLineHelper = commandLineHelper;
    }
}
