package pl.edu.agh.mabics;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import pl.edu.agh.mabics.agents.implementation.AgentType;
import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 13.12.12
 * Time: 19:04
 */
public class MabicsGUI {

    private JTabbedPane tabbedPane1;
    private JTextField numberOfSeriesTextField;
    private JPanel IntersectionImage;
    private JButton IntersectionFileChooseButton;
    private JTextField intersectionFilePath;
    private JRadioButton simpleEnvironmentRadioButton;
    private JRadioButton other1RadioButton;
    private JRadioButton other2RadioButton;
    private JTextField numberOfAgentsLeftTextField;
    private JTextField numberOfAgentsDownTextField;
    private JTextField LeftTopCornerDownTextField;
    private JTextField RightDownCornerDownTextField;
    private JButton generateRandomButtonLeft;
    private JButton generateRandomButtonDown;
    private final JFileChooser fc = new JFileChooser();

    public JPanel Main;
    private JComboBox agentImplementationLeftComboBox;
    private JTextField samplingFrequencyTextField;
    private JButton runButton;
    private JButton writeToFileButton;
    private JButton readFromFileButton;
    private JPanel Environment;
    private JCheckBox performParametrsSearchCheckBox;
    private JTextField LeftTopCornerLeftTextField;
    private JTextField RightDownCornerLeftTextField;
    private JTextField EndLineLeftTextField;
    private JCheckBox generateForEveryGameCheckBox;
    private JCheckBox plotNumberOfCollisionsCheckBox;
    private JCheckBox plotTimeOfIntersectionCrossingAverageCheckBox;
    private JCheckBox plotTimeOfIntersectionCorssingLastCheckBox;
    private JTextField numberOfGamesTextField;
    private JTextField EndLineDownTextField;
    private JComboBox agentImplementationDownComboBox;
    private JPanel agentsPanelDown;
    private JPanel agentsPanelLeft;
    private JButton IntersectionFileShowButton;
    private JPanel agentsPanelLeftWrapper = new JPanel();
    private JPanel agentsPanelDownWrapper = new JPanel();


    public MabicsGUI() {
        $$$setupUI$$$();
        agentImplementationDownComboBox.setModel(new DefaultComboBoxModel(AgentType.values()));
        agentImplementationLeftComboBox.setModel(new DefaultComboBoxModel(AgentType.values()));

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("MabicsGUI");
        frame.setContentPane(new MabicsGUI().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    private void createUIComponents() {
        Main = new JPanel();
    }


    public void setData(FormBean data) {
        plotTimeOfIntersectionCrossingAverageCheckBox.setSelected(data.isPlotTimeOfIntersectionCrossingAverage());
        plotNumberOfCollisionsCheckBox.setSelected(data.isPlotNumberOfCollision());
        numberOfSeriesTextField.setText(data.getNumberOfSeries());
        samplingFrequencyTextField.setText(data.getSamplingFrequency());
        numberOfGamesTextField.setText(data.getNumberOfGames());
        plotTimeOfIntersectionCorssingLastCheckBox.setSelected(data.isPlotTimeOfIntersection());
        intersectionFilePath.setText(data.getIntersectionFilePath());
        LeftTopCornerDownTextField.setText(data.getLeftTopCornerDownCoordinates());
        RightDownCornerDownTextField.setText(data.getRightDownCornerDownCoordinates());
        LeftTopCornerLeftTextField.setText(data.getLeftTopCornerLeftCoordinates());
        RightDownCornerLeftTextField.setText(data.getRightDownCornerLeftCoordinates());
        EndLineLeftTextField.setText(data.getEndLineLeft());
        EndLineDownTextField.setText(data.getEndLineDown());
        numberOfAgentsLeftTextField.setText(data.getNumberOfAgentsLeft());
        numberOfAgentsDownTextField.setText(data.getNumberOfAgentsDown());
        performParametrsSearchCheckBox.setSelected(data.isPerformParametersSearch());
        generateForEveryGameCheckBox.setSelected(data.isGenerateForEveryGame());
        agentImplementationDownComboBox.setSelectedItem(data.getImplementationAgentDown());
        agentImplementationLeftComboBox.setSelectedItem(data.getImplementationAgentLeft());

        agentsPanelDownWrapper.removeAll();
        agentsPanelLeftWrapper.removeAll();
        agentsPanelDown = data.getDownAgentsData();
        agentsPanelLeft = data.getLeftAgentsData();
        agentsPanelDownWrapper.add(agentsPanelDown, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        agentsPanelLeftWrapper.add(agentsPanelLeft, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        agentsPanelLeftWrapper.revalidate();
        agentsPanelDownWrapper.revalidate();
    }

    public FormBean getData(FormBean data) {
        data.setPlotTimeOfIntersectionCrossingAverage(plotTimeOfIntersectionCrossingAverageCheckBox.isSelected());
        data.setPlotNumberOfCollision(plotNumberOfCollisionsCheckBox.isSelected());
        data.setNumberOfSeries(numberOfSeriesTextField.getText());
        data.setSamplingFrequency(samplingFrequencyTextField.getText());
        data.setNumberOfGames(numberOfGamesTextField.getText());
        data.setPlotTimeOfIntersection(plotTimeOfIntersectionCorssingLastCheckBox.isSelected());
        data.setIntersectionFilePath(intersectionFilePath.getText());
        data.setLeftTopCornerDownCoordinates(LeftTopCornerDownTextField.getText());
        data.setRightDownCornerDownCoordinates(RightDownCornerDownTextField.getText());
        data.setLeftTopCornerLeftCoordinates(LeftTopCornerLeftTextField.getText());
        data.setRightDownCornerLeftCoordinates(RightDownCornerLeftTextField.getText());
        data.setEndLineLeft(EndLineLeftTextField.getText());
        data.setEndLineDown(EndLineDownTextField.getText());
        data.setNumberOfAgentsLeft(numberOfAgentsLeftTextField.getText());
        data.setNumberOfAgentsDown(numberOfAgentsDownTextField.getText());
        data.setImplementationAgentLeft((AgentType) agentImplementationLeftComboBox.getSelectedItem());
        data.setImplementationAgentDown((AgentType) agentImplementationDownComboBox.getSelectedItem());
        data.setPerformParametersSearch(performParametrsSearchCheckBox.isSelected());
        data.setGenerateForEveryGame(generateForEveryGameCheckBox.isSelected());
        data.setLeftAgentsData(agentsPanelLeft);
        data.setDownAgentsData(agentsPanelDown);
        return data;
    }

    //TODO not all data checked
    public boolean isModified(FormBean data) {
        if (plotTimeOfIntersectionCrossingAverageCheckBox.isSelected() != data.isPlotTimeOfIntersectionCrossingAverage())
            return true;
        if (plotNumberOfCollisionsCheckBox.isSelected() != data.isPlotNumberOfCollision()) return true;
        if (numberOfSeriesTextField.getText() != null ? !numberOfSeriesTextField.getText().equals(data.getNumberOfSeries()) : data.getNumberOfSeries() != null)
            return true;
        if (samplingFrequencyTextField.getText() != null ? !samplingFrequencyTextField.getText().equals(data.getSamplingFrequency()) : data.getSamplingFrequency() != null)
            return true;
        if (numberOfGamesTextField.getText() != null ? !numberOfGamesTextField.getText().equals(data.getNumberOfGames()) : data.getNumberOfGames() != null)
            return true;
        if (plotTimeOfIntersectionCorssingLastCheckBox.isSelected() != data.isPlotTimeOfIntersection()) return true;
        if (intersectionFilePath.getText() != null ? !intersectionFilePath.getText().equals(data.getIntersectionFilePath()) : data.getIntersectionFilePath() != null)
            return true;
        if (LeftTopCornerDownTextField.getText() != null ? !LeftTopCornerDownTextField.getText().equals(data.getLeftTopCornerDownCoordinates()) : data.getLeftTopCornerDownCoordinates() != null)
            return true;
        if (RightDownCornerDownTextField.getText() != null ? !RightDownCornerDownTextField.getText().equals(data.getRightDownCornerDownCoordinates()) : data.getRightDownCornerDownCoordinates() != null)
            return true;
        if (LeftTopCornerLeftTextField.getText() != null ? !LeftTopCornerLeftTextField.getText().equals(data.getLeftTopCornerLeftCoordinates()) : data.getLeftTopCornerLeftCoordinates() != null)
            return true;
        if (RightDownCornerLeftTextField.getText() != null ? !RightDownCornerLeftTextField.getText().equals(data.getRightDownCornerLeftCoordinates()) : data.getRightDownCornerLeftCoordinates() != null)
            return true;
        if (EndLineLeftTextField.getText() != null ? !EndLineLeftTextField.getText().equals(data.getEndLineLeft()) : data.getEndLineLeft() != null)
            return true;
        if (EndLineDownTextField.getText() != null ? !EndLineDownTextField.getText().equals(data.getEndLineDown()) : data.getEndLineDown() != null)
            return true;
        if (numberOfAgentsLeftTextField.getText() != null ? !numberOfAgentsLeftTextField.getText().equals(data.getNumberOfAgentsLeft()) : data.getNumberOfAgentsLeft() != null)
            return true;
        if (numberOfAgentsDownTextField.getText() != null ? !numberOfAgentsDownTextField.getText().equals(data.getNumberOfAgentsDown()) : data.getNumberOfAgentsDown() != null)
            return true;
        if (performParametrsSearchCheckBox.isSelected() != data.isPerformParametersSearch()) return true;
        if (generateForEveryGameCheckBox.isSelected() != data.isGenerateForEveryGame()) return true;
        return false;
    }

    public JPanel getIntersectionImage() {
        return IntersectionImage;
    }

    public JButton getIntersectionFileChooseButton() {
        return IntersectionFileChooseButton;
    }

    public JTextField getIntersectionFilePath() {
        return intersectionFilePath;
    }

    public JTextField getNumberOfAgentsLeftTextField() {
        return numberOfAgentsLeftTextField;
    }

    public JTextField getNumberOfAgentsDownTextField() {
        return numberOfAgentsDownTextField;
    }

    public JButton getGenerateRandomButtonLeft() {
        return generateRandomButtonLeft;
    }

    public JButton getGenerateRandomButtonDown() {
        return generateRandomButtonDown;
    }

    public JFileChooser getFc() {
        return fc;
    }

    public JPanel getAgentsPanelDown() {
        return agentsPanelDown;
    }

    public JPanel getAgentsPanelLeft() {
        return agentsPanelLeft;
    }

    public JButton getIntersectionFileShowButton() {
        return IntersectionFileShowButton;
    }

    public Component getParent() {
        return tabbedPane1;
    }


    public JTextField getLeftTopCornerLeftTextField() {
        return LeftTopCornerLeftTextField;
    }

    public JTextField getRightDownCornerLeftTextField() {
        return RightDownCornerLeftTextField;
    }

    public JTextField getRightDownCornerDownTextField() {
        return RightDownCornerDownTextField;
    }

    public JTextField getLeftTopCornerDownTextField() {
        return LeftTopCornerDownTextField;
    }

    public JButton getWriteToFileButton() {
        return writeToFileButton;
    }


    public JButton getReadFromFileButton() {
        return readFromFileButton;
    }

    public JButton getRunButton() {
        return runButton;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        Main.setLayout(new BorderLayout(0, 0));
        tabbedPane1 = new JTabbedPane();
        Main.add(tabbedPane1, BorderLayout.CENTER);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Experiment", panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, new Dimension(-1, 300), 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Number of games: ");
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Number of series:");
        panel2.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        plotTimeOfIntersectionCrossingAverageCheckBox = new JCheckBox();
        plotTimeOfIntersectionCrossingAverageCheckBox.setText("time of intersection crossing (average)");
        panel2.add(plotTimeOfIntersectionCrossingAverageCheckBox, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        plotNumberOfCollisionsCheckBox = new JCheckBox();
        plotNumberOfCollisionsCheckBox.setText("number of collisions");
        panel2.add(plotNumberOfCollisionsCheckBox, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        numberOfSeriesTextField = new JTextField();
        panel2.add(numberOfSeriesTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), new Dimension(100, -1), 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Plots to create:");
        panel2.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Sampling frequency");
        panel2.add(label4, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        samplingFrequencyTextField = new JTextField();
        samplingFrequencyTextField.setEnabled(true);
        samplingFrequencyTextField.setForeground(new Color(-3355444));
        samplingFrequencyTextField.setText("(in number of games)");
        panel2.add(samplingFrequencyTextField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), new Dimension(100, -1), 0, false));
        numberOfGamesTextField = new JTextField();
        numberOfGamesTextField.setColumns(1);
        panel2.add(numberOfGamesTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(102, 22), new Dimension(100, -1), 0, false));
        plotTimeOfIntersectionCorssingLastCheckBox = new JCheckBox();
        plotTimeOfIntersectionCorssingLastCheckBox.setText("time of intersection crossing (last)");
        panel2.add(plotTimeOfIntersectionCorssingLastCheckBox, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Environment = new JPanel();
        Environment.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Environment", Environment);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        Environment.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 250), null, null, 0, false));
        IntersectionImage = new JPanel();
        IntersectionImage.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        IntersectionImage.setBackground(new Color(-1));
        panel3.add(IntersectionImage, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Choose intersection");
        panel4.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        IntersectionFileChooseButton = new JButton();
        IntersectionFileChooseButton.setText("...");
        panel4.add(IntersectionFileChooseButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intersectionFilePath = new JTextField();
        panel4.add(intersectionFilePath, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        IntersectionFileShowButton = new JButton();
        IntersectionFileShowButton.setText("Show!");
        panel4.add(IntersectionFileShowButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Environment Physic:");
        panel5.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        simpleEnvironmentRadioButton = new JRadioButton();
        simpleEnvironmentRadioButton.setText("Simple");
        panel5.add(simpleEnvironmentRadioButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        other1RadioButton = new JRadioButton();
        other1RadioButton.setText("Other1");
        panel5.add(other1RadioButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        other2RadioButton = new JRadioButton();
        other2RadioButton.setText("Other2");
        panel5.add(other2RadioButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Agents", panel6);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(10, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel7.add(panel8, new GridConstraints(0, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(200, -1), null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Number of agents:");
        panel8.add(label7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Starting area:");
        panel8.add(label8, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LeftTopCornerDownTextField = new JTextField();
        LeftTopCornerDownTextField.setText("(x,y)");
        panel8.add(LeftTopCornerDownTextField, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), new Dimension(80, -1), 0, false));
        RightDownCornerDownTextField = new JTextField();
        RightDownCornerDownTextField.setText("(x,y)");
        panel8.add(RightDownCornerDownTextField, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), new Dimension(80, -1), 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Down");
        panel8.add(label9, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generateRandomButtonLeft = new JButton();
        generateRandomButtonLeft.setText("Generate Random");
        panel8.add(generateRandomButtonLeft, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generateRandomButtonDown = new JButton();
        generateRandomButtonDown.setText("Generate Random");
        panel8.add(generateRandomButtonDown, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agentImplementationLeftComboBox = new JComboBox();
        panel8.add(agentImplementationLeftComboBox, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Change Implementation");
        label10.setVerifyInputWhenFocusTarget(true);
        panel8.add(label10, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        agentImplementationDownComboBox = new JComboBox();
        panel8.add(agentImplementationDownComboBox, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("LeftTopCorner");
        panel8.add(label11, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("RightDownCorner");
        panel8.add(label12, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        LeftTopCornerLeftTextField = new JTextField();
        LeftTopCornerLeftTextField.setText("(x,y)");
        panel8.add(LeftTopCornerLeftTextField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), new Dimension(80, -1), 0, false));
        RightDownCornerLeftTextField = new JTextField();
        RightDownCornerLeftTextField.setText("(x,y)");
        panel8.add(RightDownCornerLeftTextField, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), new Dimension(80, -1), 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("End line:");
        panel8.add(label13, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        EndLineLeftTextField = new JTextField();
        EndLineLeftTextField.setText("x");
        panel8.add(EndLineLeftTextField, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), new Dimension(80, -1), 0, false));
        EndLineDownTextField = new JTextField();
        EndLineDownTextField.setText("y");
        panel8.add(EndLineDownTextField, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), new Dimension(80, -1), 0, false));
        numberOfAgentsLeftTextField = new JTextField();
        panel8.add(numberOfAgentsLeftTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), new Dimension(50, -1), 0, false));
        numberOfAgentsDownTextField = new JTextField();
        panel8.add(numberOfAgentsDownTextField, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), new Dimension(50, -1), 0, false));
        performParametrsSearchCheckBox = new JCheckBox();
        performParametrsSearchCheckBox.setText("Perform parametrs search");
        panel8.add(performParametrsSearchCheckBox, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("Change Implementation");
        label14.setVerifyInputWhenFocusTarget(true);
        panel8.add(label14, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generateForEveryGameCheckBox = new JCheckBox();
        generateForEveryGameCheckBox.setText("Generate for every game");
        panel8.add(generateForEveryGameCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("Left");
        panel8.add(label15, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel8.add(scrollPane1, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 400), new Dimension(-1, 400), new Dimension(-1, 400), 0, false));
        agentsPanelLeftWrapper.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        agentsPanelLeftWrapper.setAutoscrolls(true);
        scrollPane1.setViewportView(agentsPanelLeftWrapper);
        agentsPanelLeft = new JPanel();
        agentsPanelLeft.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        agentsPanelLeft.setAutoscrolls(true);
        agentsPanelLeftWrapper.add(agentsPanelLeft, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel8.add(scrollPane2, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 400), new Dimension(-1, 400), new Dimension(-1, 400), 0, false));
        agentsPanelDownWrapper = new JPanel();
        agentsPanelDownWrapper.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        agentsPanelDownWrapper.setAutoscrolls(true);
        scrollPane2.setViewportView(agentsPanelDownWrapper);
        agentsPanelDown = new JPanel();
        agentsPanelDown.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        agentsPanelDown.setAutoscrolls(true);
        agentsPanelDownWrapper.add(agentsPanelDown, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Parameters search", panel9);
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        Main.add(panel10, BorderLayout.SOUTH);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel10.add(panel11, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        runButton = new JButton();
        runButton.setText("Run!");
        panel11.add(runButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        writeToFileButton = new JButton();
        writeToFileButton.setText("Write to file");
        panel11.add(writeToFileButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        readFromFileButton = new JButton();
        readFromFileButton.setText("Read from file");
        panel11.add(readFromFileButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Main;
    }
}
