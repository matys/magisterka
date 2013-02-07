package pl.edu.agh.mabics.ui.listeners.util;

import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 07.02.13
 * Time: 14:52
 */
@Service
public class AgentListenersHelper {

    private static Random randomGenerator = new Random();

    public static JTextField generateRandomAgentPosition() {
        Integer x = randomGenerator.nextInt();
        Integer y = randomGenerator.nextInt();
        return createAgentPosition(x.toString(), y.toString());
    }

    public static JTextField generateBlankAgentPosition() {
        return createAgentPosition("x", "y");
    }


    private static JTextField createAgentPosition(String x, String y) {
        JTextField textField = new JTextField();
        textField.setText("(" + x + "," + y + ")");
        return textField;
    }

    public static JLabel createAgentName(String rowId) {
        final JLabel label = new JLabel();
        label.setText("agent" + rowId);
        return label;
    }
}
