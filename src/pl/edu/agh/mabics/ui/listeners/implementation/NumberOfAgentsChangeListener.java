package pl.edu.agh.mabics.ui.listeners.implementation;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import pl.edu.agh.mabics.ui.listeners.helpers.AgentListenersHelper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 05.02.13
 * Time: 17:19
 */
public class NumberOfAgentsChangeListener implements DocumentListener {
    private JPanel agentsPanel;
    private JTextField numberOfAgentsTextField;
    private String type;


    public NumberOfAgentsChangeListener(JPanel agentsPanel, JTextField numberOfAgentsTextField, String type) {
        this.agentsPanel = agentsPanel;
        this.numberOfAgentsTextField = numberOfAgentsTextField;
        this.type = type;
    }

    public void changedUpdate(DocumentEvent e) {
        updateAgentsPanel(agentsPanel, numberOfAgentsTextField.getText());
    }

    public void removeUpdate(DocumentEvent e) {
        updateAgentsPanel(agentsPanel, numberOfAgentsTextField.getText());
    }

    public void insertUpdate(DocumentEvent e) {
        updateAgentsPanel(agentsPanel, numberOfAgentsTextField.getText());
    }

    private void updateAgentsPanel(JPanel agentsPanel, String text) {
        Integer rowsNumber = parseNumberOfAgents(text);

        agentsPanel.removeAll();
        agentsPanel.setLayout(new GridLayoutManager(rowsNumber > 0 ? rowsNumber : 1, 2, new Insets(0, 0, 0, 0), -1, -1));
        for (int rowId = 0; rowId < rowsNumber; rowId++) {
            addRowToAgentPanel(agentsPanel, rowId);
        }
        agentsPanel.revalidate();
    }

    private Integer parseNumberOfAgents(String text) {
        Integer rowsNumber = null;
        try {
            rowsNumber = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            rowsNumber = 0;
        }
        return rowsNumber;
    }

    private void addRowToAgentPanel(JPanel agentsPanel, Integer rowId) {
        final JLabel label = AgentListenersHelper.createAgentName(rowId.toString(), type);
        agentsPanel.add(label, new GridConstraints(rowId, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JTextField textField = AgentListenersHelper.generateBlankAgentPosition();
        agentsPanel.add(textField, new GridConstraints(rowId, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), null, new Dimension(30, -1), 0, false));
    }

}
