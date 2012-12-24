package pl.edu.agh.mabics;

import pl.edu.agh.mabics.ui.datamodel.FormBean;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 13.12.12
 * Time: 19:04
 */
public class ExperimentsGUI {
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
    private JButton generateRandomButton;
    private JButton generateRandomButton1;
    private JTextField xYTextField4;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ExperimentsGUI");
        frame.setContentPane(new ExperimentsGUI().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

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
    }

    public void getData(FormBean data) {
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
        data.setPerformParametersSearch(performParametrsSearchCheckBox.isSelected());
        data.setGenerateForEveryGame(generateForEveryGameCheckBox.isSelected());
    }

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
}
