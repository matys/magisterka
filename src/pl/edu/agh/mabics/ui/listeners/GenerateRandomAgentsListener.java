package pl.edu.agh.mabics.ui.listeners;

import pl.edu.agh.mabics.ui.datamodel.Coordinates;
import pl.edu.agh.mabics.ui.listeners.util.AgentListenersHelper;

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
    private Coordinates leftTopCorner;
    private Coordinates rightDownCorner;

    public GenerateRandomAgentsListener(JPanel agentsPanel) {
        this.agentsPanel = agentsPanel;
//        this.leftTopCorner = leftTopCorner;
//        this.rightDownCorner = rightDownCorner;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (Component agent : agentsPanel.getComponents()) {
            if (agent instanceof JTextField) {
                ((JTextField) agent).setText(AgentListenersHelper.generateRandomAgentPosition().getText());
            }
        }
    }


}
