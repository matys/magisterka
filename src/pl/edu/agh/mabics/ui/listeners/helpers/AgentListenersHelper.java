package pl.edu.agh.mabics.ui.listeners.helpers;

import org.springframework.stereotype.Service;
import pl.edu.agh.mabics.ui.datamodel.util.Coordinates;

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

    /**
     * generates random point according to boundaries specified by user. LeftDown corner of board is (0,0)
     *
     * @param leftTopCoordinates
     * @param rightDownCoordinates
     * @return
     */
    public static JTextField generateRandomAgentPosition(Coordinates leftTopCoordinates, Coordinates rightDownCoordinates) {
        int leftX = leftTopCoordinates.getX();
        int rightX = rightDownCoordinates.getX();
        int topY = leftTopCoordinates.getY();
        int downY = rightDownCoordinates.getY();
        Integer x = scaleCoordinate(leftX, rightX);
        Integer y = scaleCoordinate(downY, topY);
        return createAgentPosition(x.toString(), y.toString());
    }

    private static Integer scaleCoordinate(int smaller, int grater) {
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
}
