package pl.edu.agh.mabics.ui.datamodel.util;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.platform.converters.IConverter;
import pl.edu.agh.mabics.ui.datamodel.beans.AgentData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.02.13
 * Time: 19:35
 */
@Service
public class AgentDataReverseConverter implements IConverter<JPanel, List<AgentData>> {

    private CoordinatesReverseConverter coordinatesReverseConverter;

    @Autowired
    public void setCoordinatesReverseConverter(CoordinatesReverseConverter coordinatesReverseConverter) {
        this.coordinatesReverseConverter = coordinatesReverseConverter;
    }

    @Override
    public JPanel convert(List<AgentData> input) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayoutManager(input.size(), 2, new Insets(0, 0, 0, 0), -1, -1));
        panel.setAutoscrolls(true);
        int rowId = 0;
        for (AgentData agentData : input) {
            addRowToAgentPanel(panel, rowId, agentData);
            rowId++;
        }
        return panel;
    }

    private void addRowToAgentPanel(JPanel agentsPanel, Integer rowId, AgentData agentData) {
        final JLabel label = new JLabel();
        label.setText(agentData.getName());
        agentsPanel.add(label, new GridConstraints(rowId, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JTextField textField = new JTextField();
        textField.setText(coordinatesReverseConverter.convert(agentData.getLocation()));
        agentsPanel.add(textField, new GridConstraints(rowId, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(30, -1), null, new Dimension(30, -1), 0, false));
    }
}
