package pl.edu.agh.mabics.ui.listeners.implementation;

import pl.edu.agh.mabics.ui.listeners.helpers.IIntersectionConfigurationHelper;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 11.02.13
 * Time: 15:07
 */
public class IntersectionImageChoiceListener extends MouseAdapter {

    private static final String INTERSECTION_FILE_DESCRIPTION = ".bmp - bitmap with intersection";
    private static final String INTERSECTION_FILE_EXTENSIONS = "bmp";
    private static final String INTERSECTION_FILE_DEFAULT_DIRECTORY = "C:\\Users\\Mateusz\\Desktop\\nauka\\Studia";
    private JFileChooser fc;
    private Component fileChooserParent;
    private IIntersectionConfigurationHelper intersectionConfigurationHelper;

    public IntersectionImageChoiceListener(JFileChooser fc, Component fileChooserParent, IIntersectionConfigurationHelper intersectionConfigurationHelper) {
        this.fc = fc;
        this.fileChooserParent = fileChooserParent;
        this.intersectionConfigurationHelper = intersectionConfigurationHelper;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        fc.setFileFilter(new FileNameExtensionFilter(INTERSECTION_FILE_DESCRIPTION, INTERSECTION_FILE_EXTENSIONS));
        fc.setCurrentDirectory(new File(INTERSECTION_FILE_DEFAULT_DIRECTORY));
        int returnVal = fc.showOpenDialog(fileChooserParent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            intersectionConfigurationHelper.setIntersectionFilePath(file.getPath());
        }
    }
}
