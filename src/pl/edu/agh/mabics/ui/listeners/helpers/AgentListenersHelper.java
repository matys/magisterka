package pl.edu.agh.mabics.ui.listeners.helpers;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.ui.datamodel.beans.AgentData;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

import javax.swing.*;
import java.util.List;
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

    /**
     * generates random point according to boundaries specified by user. LeftDown corner of board is (0,0)
     *
     * @param leftTopCoordinates
     * @param rightDownCoordinates
     * @return
     */
    public static JTextField generateRandomAgentPosition(Coordinates leftTopCoordinates,
                                                         Coordinates rightDownCoordinates) {
        int leftX = leftTopCoordinates.getX();
        int rightX = rightDownCoordinates.getX();
        int topY = leftTopCoordinates.getY();
        int downY = rightDownCoordinates.getY();
        Integer x = randomCoordinateInRange(leftX, rightX);
        Integer y = randomCoordinateInRange(downY, topY);
        return createAgentPosition(x.toString(), y.toString());
    }

    private static Integer randomCoordinateInRange(int smaller, int grater) {
        assert smaller < grater;
        int random = randomGenerator.nextInt();
        return smaller + Math.abs(random) % (grater - smaller);
    }

    public static JTextField generateRandomAgentPosition() {
        Integer x = randomGenerator.nextInt();
        Integer y = randomGenerator.nextInt();
        return createAgentPosition(x.toString(), y.toString());
    }

    public static JTextField generateBlankAgentPosition() {
        return createAgentPosition("0", "0");
    }


    private static JTextField createAgentPosition(String x, String y) {
        JTextField textField = new JTextField();
        textField.setText("(" + x + "," + y + ")");
        return textField;
    }

    public static JLabel createAgentName(String rowId, String type) {
        final JLabel label = new JLabel();
        label.setText("agent" + type + rowId);
        return label;
    }

    public static boolean agentAlreadyCreated(JTextField newAgent, List<JTextField> oldAgents) {
        for (JTextField oldAgent : oldAgents) {
            if (oldAgent.getText().compareTo(newAgent.getText()) == 0) {
                return true;
            }
        }
        return false;
    }

    public static JTextField createUniqueRandomAgent(List<JTextField> alreadyAddedAgents,
                                                     Coordinates leftTopCoordinates, Coordinates rightDownCoordinates) {
        int counter = 0;
        while (true) {
            JTextField randomAgent = generateRandomAgentPosition(leftTopCoordinates, rightDownCoordinates);
            if (!AgentListenersHelper.agentAlreadyCreated(randomAgent, alreadyAddedAgents)) {
                return randomAgent;
            }
            counter++;
            if (counter > 200) {
                throw new IllegalArgumentException("to less space for agents");
            }
        }
    }

    public static AgentData createUniqueRandomAgentData(List<AgentData> alreadyAddedAgents,
                                                        Coordinates leftTopCoordinates,
                                                        Coordinates rightDownCoordinates) {
        AgentData randomAgent = new AgentData();
        int counter = 0;
        while (true) {
            fillRandomAgentPosition(randomAgent, leftTopCoordinates, rightDownCoordinates);
            if (!AgentListenersHelper.agentAlreadyCreated(randomAgent, alreadyAddedAgents)) {
                return randomAgent;
            }
            counter++;
            if (counter > 200) {
                throw new IllegalArgumentException("to less space for agents");
            }
        }
    }

    private static void fillRandomAgentPosition(AgentData randomAgent, Coordinates leftTopCoordinates,
                                                Coordinates rightDownCoordinates) {
        int leftX = leftTopCoordinates.getX();
        int rightX = rightDownCoordinates.getX();
        int topY = leftTopCoordinates.getY();
        int downY = rightDownCoordinates.getY();
        Integer x = randomCoordinateInRange(leftX, rightX);
        Integer y = randomCoordinateInRange(downY, topY);
        randomAgent.setLocation(new Coordinates(x, y));
    }

    private static boolean agentAlreadyCreated(AgentData newAgent, List<AgentData> oldAgents) {
        for (AgentData oldAgent : oldAgents) {
            if (oldAgent.getLocation().getY() == newAgent.getLocation().getY() && oldAgent.getLocation()
                    .getX() == newAgent.getLocation().getX()) {
                return true;
            }
        }
        return false;
    }
}
