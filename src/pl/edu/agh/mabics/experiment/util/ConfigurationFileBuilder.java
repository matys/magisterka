package pl.edu.agh.mabics.experiment.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.ui.datamodel.beans.AgentData;
import pl.edu.agh.mabics.ui.datamodel.beans.IntersectionConfiguration;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 14.02.13
 * Time: 16:42
 */
@Service
public class ConfigurationFileBuilder {

    private FileHelper fileHelper;
    private BufferedWriter bufferedWriter;
    private String fileName;

    public void createNewConfigurationFile(String configFilePath, String fileName) {
        this.bufferedWriter = fileHelper.createBufferedWriter(configFilePath + fileName);
        this.fileName = fileName;
    }

    public void writeIntersectionData(IntersectionConfiguration configuration) {
        try {
            this.bufferedWriter.write(configuration.getIntersectionFilePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeAgentData(AgentData agentData, List<Coordinates> stopPoints, int port) {
        try {
            String line = buildLine(agentData, stopPoints, port);
            bufferedWriter.write(line);
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildLine(AgentData agentData, List<Coordinates> stopPoints, int port) {
        return "\n" + agentData.getName() + " " + "((" + agentData.getLocation().getX() + "," + agentData.getLocation()
                .getY() + "),0,(0,0)) " + "[" + listToString(stopPoints) + "]" + " localhost:" + port;
    }

    private String listToString(List<Coordinates> stopPoints) {
        String returnValue = "";
        for (Coordinates point : stopPoints) {
            returnValue = returnValue + point.toString() + ",";
        }
        return returnValue.substring(0, returnValue.length() - 1);
    }


    public String getFile() {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @Autowired
    public void setFileHelper(FileHelper fileHelper) {
        this.fileHelper = fileHelper;
    }
}
