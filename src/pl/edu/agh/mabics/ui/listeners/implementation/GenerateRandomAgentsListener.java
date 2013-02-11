package pl.edu.agh.mabics.ui.listeners.implementation;

import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;
import pl.edu.agh.mabics.ui.datamodel.util.CoordinatesConverter;
import pl.edu.agh.mabics.ui.listeners.helpers.AgentListenersHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.02.13
 * Time: 15:08
 */
public class GenerateRandomAgentsListener extends MouseAdapter {

    private JPanel agentsPanel;
    private JTextField leftTopCornerField;
    private JTextField rightDownCornerField;

    public GenerateRandomAgentsListener(JPanel agentsPanel, JTextField leftTopCornerField, JTextField rightDownCornerField) {
        this.agentsPanel = agentsPanel;
        this.leftTopCornerField = leftTopCornerField;
        this.rightDownCornerField = rightDownCornerField;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Coordinates leftTopCoordinates = CoordinatesConverter.convert(leftTopCornerField);
        Coordinates rightTopCoordinates = CoordinatesConverter.convert(rightDownCornerField);
        for (Component agent : agentsPanel.getComponents()) {
            if (agent instanceof JTextField) {
                ((JTextField) agent).setText(AgentListenersHelper.generateRandomAgentPosition(leftTopCoordinates, rightTopCoordinates).getText());
            }
        }
    }


}
