package pl.edu.agh.mabics;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 13.12.12
 * Time: 19:04
 */
public class ExperimentsGUI {
    private JTabbedPane tabbedPane1;
    private JTextField textField1;
    private JTextField textField2;
    private JCheckBox timeOfIntersectionCrossingCheckBox;
    private JCheckBox numberOfCollisionsCheckBox;
    private JTextField textField3;
    private JPanel IntersectionImage;
    private JButton button1;
    private JTextField textField4;
    private JRadioButton simpleRadioButton;
    private JRadioButton other1RadioButton;
    private JRadioButton other2RadioButton;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField xYTextField;
    private JTextField xYTextField1;
    private JTextField xYTextField2;
    private JTextField xYTextField3;
    private JCheckBox checkBox1;
    private JButton generateRandomButton;
    private JButton generateRandomButton1;
    private JCheckBox checkBox2;
    private JTextField xYTextField4;
    private JButton selectDeselectAllButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("ExperimentsGUI");
        frame.setContentPane(new ExperimentsGUI().Maina);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel Maina;
    private JComboBox comboBox1;


    private void createUIComponents() {
        Maina = new JPanel();
    }


}
