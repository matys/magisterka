package pl.edu.agh.mabics.experiment.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.util.CommandLineHelper;

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
    private CommandLineHelper commandLineHelper;
    private FileHelper fileHelper;

    public List<String> createGraphs(List<String> statisticFiles, String outputDirName, Integer samplingFrequency) {
        List<String> outputFilesNames = new ArrayList<String>();
        for (String statisticFile : statisticFiles) {
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("folder", outputDirName);
            parameters.put("fileName", statisticFile);
            parameters.put("format", OUTPUT_FORMAT);
            parameters.put("frequency", samplingFrequency.toString());
            String outputFileName = fileHelper.removeExtension(statisticFile);
            parameters.put("title", outputFileName);
            String command = "gnuplot -e " + toGnuplotCommand(parameters) + " plot_statistics.p";
            commandLineHelper.runCommand(new String[]{command}, false);
            outputFilesNames.add(outputFileName + "." + OUTPUT_FORMAT);
        }
        return outputFilesNames;
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
}
