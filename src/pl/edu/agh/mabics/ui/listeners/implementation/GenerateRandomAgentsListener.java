package pl.edu.agh.mabics.ui.listeners.implementation;

import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;
import pl.edu.agh.mabics.ui.datamodel.util.CoordinatesConverter;
import pl.edu.agh.mabics.ui.listeners.helpers.AgentListenersHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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

    public GenerateRandomAgentsListener(JPanel agentsPanel, JTextField leftTopCornerField,
                                        JTextField rightDownCornerField) {
        this.agentsPanel = agentsPanel;
        this.leftTopCornerField = leftTopCornerField;
        this.rightDownCornerField = rightDownCornerField;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Coordinates leftTopCoordinates = CoordinatesConverter.convert(leftTopCornerField);
        Coordinates rightDownCoordinates = CoordinatesConverter.convert(rightDownCornerField);
        java.util.List<JTextField> alreadyAddedAgents = new ArrayList<JTextField>();
        for (Component agent : agentsPanel.getComponents()) {
            if (agent instanceof JTextField) {
                JTextField randomAgent = AgentListenersHelper
                        .createUniqueRandomAgent(alreadyAddedAgents, leftTopCoordinates, rightDownCoordinates);
                alreadyAddedAgents.add(randomAgent);
                ((JTextField) agent).setText(randomAgent.getText());
            }
        }
    }


}
