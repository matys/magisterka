package pl.edu.agh.mabics.ui.datamodel.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.converters.IConverter;
import pl.edu.agh.mabics.ui.datamodel.beans.AgentData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.02.13
 * Time: 19:35
 */
@Service
public class AgentDataConverter implements IConverter<List<AgentData>, JPanel> {

    private CoordinatesConverter coordinatesConverter;

    @Autowired
    public void setCoordinatesConverter(CoordinatesConverter coordinatesConverter) {
        this.coordinatesConverter = coordinatesConverter;
    }

    @Override
    public List<AgentData> convert(JPanel input) {
        List<AgentData> agentDatas = new ArrayList<AgentData>();
        AgentData agentData = new AgentData();
        for (Component agent : input.getComponents()) {
            if (agent instanceof JTextField) {
                agentData.setLocation(coordinatesConverter.convert(((JTextField) agent).getText()));
            } else if (agent instanceof JLabel) {
                agentData.setName(((JLabel) agent).getText());
            }
            if (agentData.getName() != null && agentData.getLocation() != null) {
                agentDatas.add(agentData);
                agentData = new AgentData();
            }
        }
        return agentDatas; //:(
    }
}
